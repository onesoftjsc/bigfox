/*
 * Author: HuongNS
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */
package vn.com.onesoft.bigfox.io.message.user.sc;

import io.netty.channel.Channel;
import vn.com.onesoft.bigfox.ChatFrame;
import vn.com.onesoft.bigfox.io.message.annotations.Message;
import vn.com.onesoft.bigfox.io.message.annotations.Property;
import vn.com.onesoft.bigfox.io.message.base.MessageIn;
import vn.com.onesoft.bigfox.io.message.user.tags.Tags;

/**
 *
 * @author HuongNS
 */
@Message(tag = Tags.SC_CHAT, name = "SC_CHAT")
public class SCChat extends MessageIn {

    @Property(name = "msg")
    private String msg;

    @Override
    public void execute(Channel channel) {
        ChatFrame.getInstance().onChat(msg);
    }


    
}
