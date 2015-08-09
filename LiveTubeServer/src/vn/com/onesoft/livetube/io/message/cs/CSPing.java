/*
 * Author: QuanPH
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */

package vn.com.onesoft.livetube.io.message.cs;

import io.netty.channel.Channel;
import vn.com.onesoft.livetube.io.session.BFSessionManager;
import vn.com.onesoft.livetube.io.message.core.MessageIn;
import vn.com.onesoft.livetube.io.message.core.Tags;
import vn.com.onesoft.livetube.io.message.core.annotations.Message;
import vn.com.onesoft.livetube.io.message.core.annotations.Property;
import vn.com.onesoft.livetube.io.message.sc.SCPing;

/**
 *
 * @author QuanPH
 */
@Message(tag = Tags.CS_PING, name = "CS_PING")
public class CSPing extends MessageIn {
    
    @Property(name = "clientTime")
    private long clientTime;
    
    @Override
    public void execute(Channel channel) {
        BFSessionManager.getInstance().sendMessage(channel, new SCPing());
    }
    
}
