package vn.com.onesoft.bigfox.io.message.core.sc;

/*
 * Author: HuongNS
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */


import io.netty.channel.Channel;
import vn.com.onesoft.bigfox.io.message.annotations.Message;
import vn.com.onesoft.bigfox.io.message.annotations.Property;
import vn.com.onesoft.bigfox.io.message.base.MessageIn;
import vn.com.onesoft.bigfox.io.message.core.tags.CoreTags;

/**
 *
 * @author HuongNS
 */
@Message(tag = CoreTags.SC_PING, name = "SC_PING", isCore = true)
public class SCPing extends MessageIn {

    @Property(name = "serverTime")
    private long serverTime;

    @Override
    public void execute(Channel channel) {
        
    }

}
