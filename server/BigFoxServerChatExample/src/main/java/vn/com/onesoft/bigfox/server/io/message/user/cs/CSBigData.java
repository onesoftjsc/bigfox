/*
 * Author: QuanPH
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */

package vn.com.onesoft.bigfox.server.io.message.user.cs;

import io.netty.channel.Channel;
import vn.com.onesoft.bigfox.server.io.messaannotationsons.Property;
import vn.com.onesoft.bigfox.server.io.message.annotations.Message;
import vn.com.onesoft.bigfox.server.io.message.base.MessageIn;
import vn.com.onesoft.bigfox.server.io.message.user.tags.Tags;

/**
 *
 * @author QuanPH
 */
@Message(tag = Tags.CS_BIGDATA, name = "CS_BIGDATA")
public class CSBigData extends MessageIn {

    @Property(name = "data")
    private byte[] data;

    @Override
    public void execute(Channel channel) {
        int a = 0;
    }
}
