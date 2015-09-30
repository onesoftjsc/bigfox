/*
 * Author: QuanPH
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */
package vn.com.onesoft.bigfox.server.example.chat;

import java.io.File;
import vn.com.onesoft.bigfox.server.helper.classmanager.MonitorFileChanged;
import vn.com.onesoft.bigfox.server.io.core.socket.SocketManager;
import vn.com.onesoft.bigfox.server.io.core.websocket.WebSocketManager;
import vn.com.onesoft.bigfox.server.io.core.zone.BFZoneManager;
import vn.com.onesoft.bigfox.server.io.message.base.MessageExecute;
import vn.com.onesoft.bigfox.server.main.BFConfig;
import vn.com.onesoft.bigfox.server.telnet.TelnetManager;

/**
 *
 * @author QuanPH
 */
public class Main {

    public static void main(String[] args) throws Exception {
//            vn.com.onesoft.bigfox.server.main.Main.main(args);

        BFZoneManager.getInstance().loadZone(new File(".").getCanonicalPath() + "/target/classes");
        MonitorFileChanged.getInstance();
        MessageExecute.getInstance();
        BFConfig.getInstance();
        SocketManager.getInstance();
        WebSocketManager.getInstance();
        TelnetManager.getInstance();
    }
}
