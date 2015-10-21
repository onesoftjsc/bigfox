/*
 * Author: QuanPH
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */

package vn.com.onesoft.time.client.io.message.user.sc;

import io.netty.channel.Channel;
import vn.com.onesoft.bigfox.io.message.annotations.Message;
import vn.com.onesoft.bigfox.io.message.annotations.Property;
import vn.com.onesoft.bigfox.io.message.base.BFLogger;
import vn.com.onesoft.bigfox.io.message.base.MessageIn;
import vn.com.onesoft.time.client.io.message.user.tags.Tags;

/**
 *
 * @author QuanPH
 */
@Message(tag = Tags.SC_GET_TIME, name = "SC_GET_TIME")
public class SCGetTime extends MessageIn{

    @Property(name = "serverTime")
    private String serverTime;

    @Override
    public void execute(Channel channel) {
        BFLogger.getInstance().info("Server time: " + serverTime);
    }

}
