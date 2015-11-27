/*
 * Author: QuanPH
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */
package vn.com.onesoft.time.server.main;

import vn.com.onesoft.bigfox.server.io.core.business.zone.BFZoneActivity;
import vn.com.onesoft.bigfox.server.io.core.business.zone.IBFZone;
import vn.com.onesoft.bigfox.server.io.message.base.BFLogger;

/**
 *
 * @author QuanPH
 */
public class TimeActivity extends BFZoneActivity {

    private static TimeActivity _instance = null;

    public static TimeActivity getInstance() {
        return _instance;
    }

    public TimeActivity(IBFZone zone) {
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
