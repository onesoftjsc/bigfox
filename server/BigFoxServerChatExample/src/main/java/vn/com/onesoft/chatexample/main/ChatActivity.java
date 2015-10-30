/*
 * Author: QuanPH
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */
package vn.com.onesoft.chatexample.main;

import vn.com.onesoft.bigfox.server.io.core.zone.BFZoneActivity;
import vn.com.onesoft.bigfox.server.io.core.zone.IBFZone;
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
    }

    @Override
    public void afterZoneStart() {
        BFLogger.getInstance().info("AfterZoneStart");
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
