/*
 * Author: QuanPH
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */
package vn.com.onesoft.bigfox.server.io.message.core.cs;

import vn.com.onesoft.bigfox.server.io.core.business.session.BFSessionManager;
import vn.com.onesoft.bigfox.server.io.message.annotations.Message;
import vn.com.onesoft.bigfox.server.io.message.annotations.Property;
import vn.com.onesoft.bigfox.server.io.message.base.BFLogger;
import vn.com.onesoft.bigfox.server.io.message.base.MessageIn;
import vn.com.onesoft.bigfox.server.io.message.core.sc.SCPing;
import vn.com.onesoft.bigfox.server.io.message.core.tags.CoreTags;

/**
 *
 * @author QuanPH
 */
@Message(tag = CoreTags.CS_PING, name = "CS_PING", isCore = true)
public class CSPing extends MessageIn {
    
    @Property(name = "clientTime")
    private long clientTime;
    
    @Override
    public void execute() {

//        BFLogger.getInstance().info("CSPing: getIp = " + this.getBFSession().getIp());
//        BFLogger.getInstance().info("CSPing: device = " + this.getBFSession().getClientInfo().device);
//        BFLogger.getInstance().info("CSPing: clientTime = " + new Date(clientTime));
//        BFLogger.getInstance().info("CSPing: getSessionId = " + this.getBFSession().getSessionId());
        if (this.getBFSession() != null) {
            BFSessionManager.getInstance().sendMessage(this.getBFSession().getChannel(), new SCPing());
        } else {
            BFLogger.getInstance().info("CSPing - getBFSession==null");
        }
        
    }
    
}
