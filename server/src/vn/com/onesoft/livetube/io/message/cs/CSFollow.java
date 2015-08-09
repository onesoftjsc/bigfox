
/*
 * Author: HuongNS
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */
package vn.com.onesoft.livetube.io.message.cs;

import io.netty.channel.Channel;
import org.hibernate.Query;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import vn.com.onesoft.livetube.db.DBUserSubscribe;
import vn.com.onesoft.livetube.db.util.HibernateFactoryUtil;
import vn.com.onesoft.livetube.io.message.core.MessageIn;
import vn.com.onesoft.livetube.io.message.core.Tags;
import vn.com.onesoft.livetube.io.message.core.annotations.Message;
import vn.com.onesoft.livetube.io.message.core.annotations.Property;
import vn.com.onesoft.livetube.io.session.BFSessionManager;
import vn.com.onesoft.livetube.main.Main;

/**
 *
 * @author HuongNS
 */
@Message(tag = Tags.CS_FOLLOW, name = "CS_FOLLOW")
public class CSFollow extends MessageIn {

    @Property(name = "subcriberId")
    public int subcriberId;
    @Property(name = "action")
    public int action; // action = 0: follow, action =1: unfollow
    public static final int ACTION_FOLLOW = 0;
    public static final int ACTION_UNFOLLOW = 1;

    @Override
    public void execute(Channel channel) {
        Main.logger.info(this.getClass().getName());
        if (BFSessionManager.getInstance().getSessionByChannel(channel).getdBUser() == null) {
            return;
        }
        Session currentSession = HibernateFactoryUtil.getInstance().getCurrentSession();
        Transaction beginTransaction = null;
        int userid = BFSessionManager.getInstance().getSessionByChannel(channel).getdBUser().getId();
        if (action == ACTION_FOLLOW) {

            try {
                beginTransaction = currentSession.beginTransaction();

                DBUserSubscribe dBUserSubscribe = new DBUserSubscribe();
                dBUserSubscribe.setSubid(subcriberId);
                dBUserSubscribe.setUserid(userid);

                currentSession.save(dBUserSubscribe);

                beginTransaction.commit();
            } catch (Exception e) {
                Main.logger.error(e.getMessage(), e);
                if (currentSession != null && currentSession.isOpen()) {
                    if (beginTransaction != null) {
                        beginTransaction.rollback();
                    }
                }
            }

        } else if (action == ACTION_UNFOLLOW) {
            try {
                beginTransaction = currentSession.beginTransaction();

                Query createQuery = currentSession.createQuery("DELETE DBUserSubscribe WHERE userid = :userid and subid = :subid")
                        .setParameter("userid",userid)
                        .setParameter("subid", subcriberId);
                
                int executeUpdate = createQuery.executeUpdate();

                beginTransaction.commit();
            } catch (Exception e) {
                Main.logger.error(e.getMessage(), e);
                if (currentSession != null && currentSession.isOpen()) {
                    if (beginTransaction != null) {
                        beginTransaction.rollback();
                    }
                }
            }

        }
    }

}
