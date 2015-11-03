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
import vn.com.onesoft.bigfox.server.helper.classmanager.MonitorFileChanged;
import vn.com.onesoft.bigfox.server.io.core.session.BFSessionManager;
import vn.com.onesoft.bigfox.server.io.core.session.IBFSession;
import vn.com.onesoft.bigfox.server.io.core.socket.SocketManager;
import vn.com.onesoft.bigfox.server.io.core.websocket.WebSocketManager;
import vn.com.onesoft.bigfox.server.io.core.zone.BFZoneManager;
import vn.com.onesoft.bigfox.server.io.core.zone.IBFZone;
import vn.com.onesoft.bigfox.server.io.message.base.MessageExecute;
import vn.com.onesoft.bigfox.server.main.BFConfig;
import vn.com.onesoft.bigfox.server.telnet.TelnetManager;
import vn.com.onesoft.chatexamain.chat.BFSessionEvent;

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
    }
}
