/*
 * Author: HuongNS
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */
package vn.com.onesoft.chatexample.server.io.message.user.cs;

import io.netty.channel.Channel;
import java.util.Calendar;
import org.hibernate.Session;
import org.hibernate.Transaction;
import vn.com.onesoft.chatexample.server.db.DBUserLog;
import vn.com.onesoft.chatexample.server.io.message.user.sc.SCChat;
import vn.com.onesoft.bigfox.server.io.core.session.BFSessionManager;
import vn.com.onesoft.bigfox.server.io.core.session.IBFSession;
import vn.com.onesoft.bigfox.server.io.core.zone.BFZoneManager;
import vn.com.onesoft.bigfox.server.io.core.zone.IBFZone;
import vn.com.onesoft.bigfox.server.io.message.annotations.Message;
import vn.com.onesoft.bigfox.server.io.message.annotations.Property;
import vn.com.onesoft.bigfox.server.io.message.base.BFLogger;
import vn.com.onesoft.bigfox.server.io.message.base.MessageIn;
import vn.com.onesoft.chatexample.main.Main;
import vn.com.onesoft.chatexample.server.db.util.HibernateFactoryUtil;
import vn.com.onesoft.chatexample.server.io.message.user.tags.Tags;


/**
 *
 * @author HuongNS
 */
@Message(tag = Tags.CS_CHAT, name = "CS_CHAT")
public class CSChat extends MessageIn {

    @Property(name = "msg")
    private String msg;

    @Override
    public void execute(Channel channel) {
         BFLogger.getInstance().info("ClassLoader CSChat " + this.getClass().getClassLoader());
        IBFSession session = BFSessionManager.getInstance().getSessionByChannel(channel);
        String name = Main.mapSessionToName.get(session);
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int mi = Calendar.getInstance().get(Calendar.MINUTE);
        int sec = Calendar.getInstance().get(Calendar.SECOND);
        String time = "" + hour + ":" + mi + ":" + sec;
        IBFZone zone = BFZoneManager.getInstance().getZone(channel);

        zone.sendMessageToAll(new SCChat(time + "\n" + name + ": "+  msg));

        Session sessionH = HibernateFactoryUtil.getInstance().getCurrentSession();
        Transaction tx = null;
        try {
            tx = sessionH.beginTransaction();
            DBUserLog dBUserLog = new DBUserLog(name, msg);

            sessionH.save(dBUserLog);
            tx.commit();
        } catch (Exception ex) {
            BFLogger.getInstance().error(ex.getMessage(), ex);
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
        }

    }

}
