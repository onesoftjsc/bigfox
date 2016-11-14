/*
 * Author: QuanPH
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */
package vn.com.onesoft.chatexample.main;

import com.google.common.collect.MapMaker;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import vn.com.onesoft.bigfox.server.helper.classmanager.MonitorFileChanged;
import vn.com.onesoft.bigfox.server.io.core.business.session.BFSessionManager;
import vn.com.onesoft.bigfox.server.io.core.business.session.IBFSession;
import vn.com.onesoft.bigfox.server.io.core.business.zone.BFZoneManager;
import vn.com.onesoft.bigfox.server.io.core.business.zone.IBFZone;
import vn.com.onesoft.bigfox.server.io.core.channel.socket.SocketManager;
import vn.com.onesoft.bigfox.server.io.core.channel.websocket.WebSocketManager;
import vn.com.onesoft.bigfox.server.io.message.base.BFLogger;
import vn.com.onesoft.bigfox.server.io.message.base.MessageExecute;
import vn.com.onesoft.bigfox.server.main.BFConfig;
import vn.com.onesoft.bigfox.server.telnet.TelnetManager;
import vn.com.onesoft.chatexample.server.db.util.HibernateFactoryUtil;


/**
 *
 * @author QuanPH
 */
public class Main {

    public static Map<IBFSession, String> mapSessionToName = new MapMaker().makeMap();

    public static void main(String[] args) throws Exception {

        IBFZone zone = BFZoneManager.getInstance().loadZone(new File(".").getCanonicalPath() + "/target/BigFoxServerChatExample");
        try {
            zone.setMonitorFolder(new File(".").getCanonicalPath() + "/target/classes");
            zone.setSessionTimeout(30);
        } catch (IOException ex) {
            Logger.getLogger(ChatActivity.class.getName()).log(Level.SEVERE, null, ex);
        }
        BFSessionManager.getInstance().setSessionEvent(new BFSessionEvent());

        MonitorFileChanged.getInstance();
        MessageExecute.getInstance();
        BFConfig.getInstance();
        SocketManager.getInstance();
        WebSocketManager.getInstance();
        TelnetManager.getInstance();
        
                BFSessionManager.getInstance().setSessionEvent(new BFSessionEvent());
        Session session = HibernateFactoryUtil.getInstance().getCurrentSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            
            tx.commit();
        } catch (Exception ex) {
            BFLogger.getInstance().error(ex.getMessage(), ex);
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
        }
    }
}
