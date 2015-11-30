/*
 * Author: QuanPH
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */
package vn.com.onesoft.chatexample.main;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import vn.com.onesoft.bigfox.server.io.core.business.session.BFSessionManager;
import vn.com.onesoft.bigfox.server.io.core.business.zone.BFZoneActivity;
import vn.com.onesoft.bigfox.server.io.core.business.zone.IBFZone;
import vn.com.onesoft.bigfox.server.io.message.base.BFLogger;

/**
 *
 * @author QuanPH
 */
public class ChatActivity extends BFZoneActivity {

    private static ChatActivity _instance = null;

    public static ChatActivity getInstance() {
        return _instance;
    }

    public ChatActivity(IBFZone zone) {
        super(zone);
        _instance = this;
        
    }

    @Override
    public void beforeZoneStart() {
        BFLogger.getInstance().info("BeforeZoneStart");
        try {
            zone.setMonitorFolder(new File(".").getCanonicalPath() + "/target/classes");
            zone.setSessionTimeout(30);
        } catch (IOException ex) {
            Logger.getLogger(ChatActivity.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void afterZoneStart() {
        BFLogger.getInstance().info("AfterZoneStart");
        BFSessionManager.getInstance().setSessionEvent(new BFSessionEvent());
    }

    @Override
    public void beforeZoneStop() {
        BFLogger.getInstance().info("BeforeZoneStop");
    }

    @Override
    public void afterZoneStop() {
        BFLogger.getInstance().info("AfterZoneStart");
    }

}
