/*
 * Author: QuanPH
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */
package vn.com.onesoft.bigfox.server.example.chat;

import com.google.common.collect.MapMaker;
import java.io.File;
import java.util.Map;
import vn.com.onesoft.bigfox.server.example.chat.db.DBUserLog;
import vn.com.onesoft.bigfox.server.example.chat.db.util.HibernateFactoryUtil;
import vn.com.onesoft.bigfox.server.helper.classmanager.MonitorFileChanged;
import vn.com.onesoft.bigfox.server.io.core.session.BFSessionManager;
import vn.com.onesoft.bigfox.server.io.core.session.IBFSession;
import vn.com.onesoft.bigfox.server.io.core.socket.SocketManager;
import vn.com.onesoft.bigfox.server.io.core.websocket.WebSocketManager;
import vn.com.onesoft.bigfox.server.io.core.zone.BFZoneManager;
import vn.com.onesoft.bigfox.server.io.message.base.BFLogger;
import vn.com.onesoft.bigfox.server.io.message.base.MessageExecute;
import vn.com.onesoft.bigfox.server.main.BFConfig;
import vn.com.onesoft.bigfox.server.telnet.TelnetManager;

/**
 *
 * @author QuanPH
 */
public class Main {
    public static Map<IBFSession, String> mapSessionToName = new MapMaker().makeMap();
    public static void main(String[] args) throws Exception {
//            vn.com.onesoft.bigfox.server.main.Main.main(args);
        BFSessionManager.getInstance().setSessionEvent(new BFSessionEvent());
        HibernateFactoryUtil.getInstance();
        DBUserLog db = new DBUserLog("1", "1");
        
        BFZoneManager.getInstance().loadZone(new File(".").getCanonicalPath() + "/target/classes");
        MonitorFileChanged.getInstance();
        MessageExecute.getInstance();
        BFConfig.getInstance();
        SocketManager.getInstance();
        WebSocketManager.getInstance();
        TelnetManager.getInstance();
        
    }
}
