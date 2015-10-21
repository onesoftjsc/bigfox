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
    public abstract void onZoneStart(BFZone zone);
    public abstract void onZoneStop(BFZone zone);
}
