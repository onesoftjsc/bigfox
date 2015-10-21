/*
 * Author: QuanPH
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */
package vn.com.onesoft.time.server.io.message.user.cs;

import io.netty.channel.Channel;
import org.hibernate.Session;
import org.hibernate.Transaction;
import vn.com.onesoft.bigfox.server.io.core.session.BFSessionManager;
import vn.com.onesoft.bigfox.server.io.message.annotations.Message;
import vn.com.onesoft.bigfox.server.io.message.base.BFLogger;
import vn.com.onesoft.bigfox.server.io.message.base.MessageIn;
import vn.com.onesoft.time.server.db.DBLog;
import vn.com.onesoft.time.server.db.HibernateFactoryUtil;
import vn.com.onesoft.time.server.io.message.user.sc.SCGetTime;
import vn.com.onesoft.time.server.io.message.user.tags.Tags;

/**
 *
 * @author QuanPH
 */
@Message(tag = Tags.CS_GET_TIME, name = "CS_GET_TIME")
public class CSGetTime extends MessageIn {

    @Override
    public void execute(Channel channel) {
        SCGetTime sCGetTime = new SCGetTime();
        BFSessionManager.getInstance().sendMessage(channel, sCGetTime);
        DBLog dbLog = new DBLog(channel.toString());

        Session session = HibernateFactoryUtil.getInstance().getCurrentSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(dbLog);
            tx.commit();
        } catch (Exception ex) {
            BFLogger.getInstance().error(ex.getMessage(), ex);
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
        }
    }

}
