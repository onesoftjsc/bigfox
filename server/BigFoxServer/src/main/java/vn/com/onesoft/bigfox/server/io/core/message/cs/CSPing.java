/*
 * Author: QuanPH
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */

package vn.com.onesoft.bigfox.server.io.core.message.cs;

import io.netty.channel.Channel;
import vn.com.onesoft.bigfox.server.io.core.annotat.messageions.Message;
import vn.com.onesoft.bigfox.server.io.core.annotat.messageions.Property;
import vn.com.onesoft.bigfox.server.io.core.message.base.MessageIn;
import vn.com.onesoft.bigfox.server.io.core.message.sc.SCPing;
import vn.com.onesoft.bigfox.server.io.core.message.tags.CoreTags;
import vn.com.onesoft.bigfox.server.io.core.session.BFSessionManager;

/**
 *
 * @author QuanPH
 */
@Message(tag = CoreTags.CS_PING, name = "CS_PING", isCore = true)
public class CSPing extends MessageIn {
    
    @Property(name = "clientTime")
    private long clientTime;
    
    @Override
    public void execute(Channel channel) {
        BFSessionManager.getInstance().sendMessage(channel, new SCPing());
    }
    
}
