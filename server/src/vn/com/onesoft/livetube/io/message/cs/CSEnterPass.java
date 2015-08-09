
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
import vn.com.onesoft.livetube.db.DBUserCode;
import vn.com.onesoft.livetube.db.util.HibernateFactoryUtil;
import vn.com.onesoft.livetube.io.message.core.MessageIn;
import vn.com.onesoft.livetube.io.message.core.Tags;
import vn.com.onesoft.livetube.io.message.core.annotations.Message;
import vn.com.onesoft.livetube.io.message.core.annotations.Property;
import vn.com.onesoft.livetube.main.LiveTubeContext;
import vn.com.onesoft.livetube.main.Main;

/**
 *
 * @author HuongNS
 */
@Message(tag = Tags.CS_ENTER_PASS, name = "CS_ENTER_PASS")
public class CSEnterPass extends MessageIn {

    @Property(name = "phone")
    public String phone;
    @Property(name = "password")
    public String password;
    @Property(name = "code")
    public String code;

    @Override
    public void execute(Channel channel) {
        Main.logger.info(this.getClass().getName());
        Session currentSession = HibernateFactoryUtil.getInstance().getCurrentSession();
        Transaction beginTransaction = currentSession.beginTransaction();

        try {
            List list = currentSession.createQuery("FROM DBUser WHERE phone = :phone")
                    .setParameter("phone", phone)
                    .setMaxResults(1).list();

            DBUser dBUser;
            if (list.size() > 0) {
                dBUser = (DBUser) list.get(0);
                dBUser.setPassword(password);

                currentSession.update(dBUser);
            } else {
                dBUser = new DBUser();
                dBUser.setMobile(phone);
                dBUser.setPassword(password);

                currentSession.save(dBUser);
            }

            DBUserCode dBUserCode = new DBUserCode();
            dBUserCode.setActive_code(code);
            dBUserCode.setMobile(phone);
            dBUserCode.setUserid(dBUser.getId());

            currentSession.save(dBUserCode);

            beginTransaction.commit();

            LiveTubeContext.mapPhoneToCode.remove(phone);
        } catch (Exception e) {
            Main.logger.error(e.getMessage(), e);
            if (beginTransaction != null && beginTransaction.isActive()) {
                beginTransaction.rollback();
            }
        }
    }
}
