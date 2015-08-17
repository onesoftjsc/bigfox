/*
 * Author: QuanPH
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */

package vn.com.onesoft.bigfox.server.io.message.core.cs;

import io.netty.channel.Channel;
import vn.com.onesoft.bigfox.server.io.message.core.MessageIn;
import vn.com.onesoft.bigfox.server.io.message.core.Tags;
import vn.com.onesoft.bigfox.server.io.message.core.annotations.Message;
import vn.com.onesoft.bigfox.server.io.message.core.annotations.Property;
import vn.com.onesoft.bigfox.server.io.message.core.sc.SCPing;
import vn.com.onesoft.bigfox.server.io.core.session.BFSessionManager;

/**
 *
 * @author QuanPH
 */
@Message(tag = Tags.CS_PING, name = "CS_PING", isCore = true)
public class CSPing extends MessageIn {
    
    @Property(name = "clientTime")
    private long clientTime;
    
    @Override
    public void execute(Channel channel) {
        BFSessionManager.getInstance().sendMessage(channel, new SCPing());
    }
    
}
