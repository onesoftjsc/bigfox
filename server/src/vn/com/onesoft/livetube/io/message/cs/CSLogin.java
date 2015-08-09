
/*
 * Author: HuongNS
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */
package vn.com.onesoft.livetube.io.message.cs;

import io.netty.channel.Channel;
import java.util.List;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import vn.com.onesoft.livetube.db.DBUser;
import vn.com.onesoft.livetube.db.util.HibernateFactoryUtil;
import vn.com.onesoft.livetube.io.message.core.MessageIn;

import vn.com.onesoft.livetube.io.message.core.Tags;
import vn.com.onesoft.livetube.io.message.core.annotations.Message;
import vn.com.onesoft.livetube.io.message.core.annotations.Property;
import vn.com.onesoft.livetube.io.message.sc.SCLogin;
import vn.com.onesoft.livetube.io.session.BFSessionManager;
import vn.com.onesoft.livetube.main.Main;

/**
 *
 * @author HuongNS
 */
@Message(tag = Tags.CS_LOGIN, name = "CS_LOGIN")
public class CSLogin extends MessageIn {

    @Property(name = "phone")
    public String phone;
    @Property(name = "password")
    public String password;

    public CSLogin() {
    }

    public CSLogin(String phone, String password) {
        super();
        this.phone = phone;
        this.password = password;
    }

    @Override
    public void execute(Channel channel) {
        Main.logger.info(this.getClass().getName());
        
        Session currentSession = HibernateFactoryUtil.getInstance().getCurrentSession();
        Transaction transaction = currentSession.getTransaction();

        try {
            transaction.begin();

            List list = currentSession.createQuery("FROM DBUser WHERE phone = :phone and password = :password")
                    .setParameter("phone", phone)
                    .setParameter("password", password).setMaxResults(1).list();

            if (list.size() > 0) {
                DBUser dBUser = (DBUser) list.get(0);
                BFSessionManager.getInstance().getSessionByChannel(channel).setdBUser(dBUser);
//                BFSessionManager.getInstance().setSessionByUserId(BFSessionManager.getInstance().getSessionByChannel(channel), dBUser.getId());
                BFSessionManager.getInstance().sendMessage(channel, new SCLogin(1));
            } else {
                BFSessionManager.getInstance().sendMessage(channel, new SCLogin(0));
            }

            transaction.commit();

        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        }

    }

}
