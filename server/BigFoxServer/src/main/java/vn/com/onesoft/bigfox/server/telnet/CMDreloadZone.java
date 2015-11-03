/*
 * Author: QuanPH
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */
package vn.com.onesoft.bigfox.server.telnet;

import vn.com.onesoft.bigfox.server.io.core.zone.BFZoneManager;
import vn.com.onesoft.bigfox.server.io.message.base.BFLogger;

/**
 *
 * @author QuanPH
 */
public class CMDreloadZone extends Command {

    @Override
    public String execute() {
        try {
            BFZoneManager.getInstance().loadZone("/Users/phamquan/livetube_all/livetube/code/core/LiveTubeServer/target/LiveTube");
            return "OK";
        } catch (Exception ex) {
            BFLogger.getInstance().error(ex.getMessage(), ex);
            return "ERROR";
        }
    }

}
