
/*
 * Author: HuongNS
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */
package vn.com.onesoft.livetube.io.message.cs;

import io.netty.channel.Channel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import vn.com.onesoft.livetube.db.DBUser;
import vn.com.onesoft.livetube.db.DBUserSubscribe;
import vn.com.onesoft.livetube.db.util.HibernateFactoryUtil;
import vn.com.onesoft.livetube.io.message.core.MessageIn;
import vn.com.onesoft.livetube.io.message.core.Tags;
import vn.com.onesoft.livetube.io.message.core.annotations.Message;
import vn.com.onesoft.livetube.io.message.objects.Subcriber;
import vn.com.onesoft.livetube.io.message.sc.SCRequestListSubcribers;
import vn.com.onesoft.livetube.io.session.BFSessionManager;
import vn.com.onesoft.livetube.io.session.IBFSession;
import vn.com.onesoft.livetube.main.Main;

/**
 *
 * @author HuongNS
 */
@Message(tag = Tags.CS_REQUEST_LIST_SUBCRIBERS, name = "CS_REQUEST_LIST_SUBCRIBERS")
public class CSRequestListSubcribers extends MessageIn {

    @Override
    public void execute(Channel channel) {
        Main.logger.info(this.getClass().getName());
        if (BFSessionManager.getInstance().getSessionByChannel(channel).getdBUser() == null) {
            return;
        }

        IBFSession sessionByChannel = BFSessionManager.getInstance().getSessionByChannel(channel);
        int userId = sessionByChannel.getdBUser().getId();

        Session currentSession = HibernateFactoryUtil.getInstance().getCurrentSession();
        Transaction beginTransaction = null;
        try {
            beginTransaction = currentSession.beginTransaction();

            List listDBUserSubscribes = currentSession.createQuery("FROM DBUserSubscribe WHERE userid = :userid")
                    .setParameter("userid", userId)
                    .list();

            Iterator iterator = listDBUserSubscribes.iterator();
            List<Subcriber> subcribers = new ArrayList<>();

            while (iterator.hasNext()) {
                DBUserSubscribe next = (DBUserSubscribe) iterator.next();
                Subcriber subcriber = new Subcriber();
                subcriber.id = next.getId();
                subcriber.name = getDBUserById(currentSession, next.getSubid()).getUsername();
                subcriber.following = getDBUserById(currentSession, next.getSubid()).getNum_subscribe();
                subcribers.add(subcriber);
            }

            BFSessionManager.getInstance().sendMessage(channel, new SCRequestListSubcribers(subcribers));

            beginTransaction.commit();
        } catch (Exception e) {
            if (currentSession != null && currentSession.isOpen()) {
                if (beginTransaction != null) {
                    beginTransaction.rollback();
                }
            }
        }
    }

    public DBUser getDBUserById(Session currentSession, int userId) {
        DBUser dBUser = null;
        List list = currentSession.createQuery("FROM DBUser WHERE id = :id")
                .setParameter("id", userId)
                .setMaxResults(1)
                .list();
        dBUser = (DBUser) list.get(0);        
        return dBUser;
    }
}
