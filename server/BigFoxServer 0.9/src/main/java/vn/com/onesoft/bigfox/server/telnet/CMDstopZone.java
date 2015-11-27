/*
 * Author: QuanPH
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */
package vn.com.onesoft.bigfox.server.telnet;

import java.io.File;
import vn.com.onesoft.bigfox.server.io.core.zone.BFZoneManager;
import vn.com.onesoft.bigfox.server.io.message.base.BFLogger;

/**
 *
 * @author QuanPH
 */
public class CMDstopZone
        extends Command {

    @Override
    public String execute() {
        try {
            String zoneName = argList.get(0);
            BFZoneManager.getInstance().removeZone(zoneName);
            return "OK";
        } catch (Exception ex) {
            BFLogger.getInstance().error(ex.getMessage(), ex);
            return "ERROR";
        }
    }
}
