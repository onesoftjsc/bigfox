/*
 * Author: HuongNS
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */

package vn.com.onesoft.bigfox.server.io.message.cs;

import io.netty.channel.Channel;
import vn.com.onesoft.bigfox.server.io.message.core.MessageIn;
import vn.com.onesoft.bigfox.server.io.message.core.Tags;
import vn.com.onesoft.bigfox.server.io.message.core.annotations.Message;
import vn.com.onesoft.bigfox.server.io.message.core.annotations.Property;
import vn.com.onesoft.bigfox.server.io.message.sc.SCChat;
import vn.com.onesoft.bigfox.server.io.session.BFSessionManager;

/**
 *
 * @author HuongNS
 */
@Message(tag = Tags.CS_CHAT, name = "CS_CHAT")
public class CSChat extends MessageIn {

    @Property(name = "msg")
    private String msg;

    @Override
    public void execute(Channel channel) {

     BFSessionManager.getInstance().sendMessage(channel, new SCChat(msg.toUpperCase()));

    }
    
    
}
