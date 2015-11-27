/*
 * Author: QuanPH
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */
package vn.com.onesoft.bigfox.server.io.core.zone;

/**
 *
 * @author QuanPH
 */
public abstract class BFZoneActivity {

    protected IBFZone zone;

    public BFZoneActivity(IBFZone zone) {
        this.zone = zone;
    }

    public IBFZone getZone() {
        return zone;
    }

    public abstract void beforeZoneStart();

    public abstract void afterZoneStart();

    public abstract void beforeZoneStop();

    public abstract void afterZoneStop();
}
