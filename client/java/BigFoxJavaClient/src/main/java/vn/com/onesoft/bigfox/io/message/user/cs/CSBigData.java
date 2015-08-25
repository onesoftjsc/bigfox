/*
 * Author: QuanPH
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */

package vn.com.onesoft.bigfox.io.message.user.cs;

import io.netty.channel.Channel;
import vn.com.onesoft.bigfox.io.message.annotations.Message;
import vn.com.onesoft.bigfox.io.message.annotations.Property;
import vn.com.onesoft.bigfox.io.message.base.MessageOut;
import vn.com.onesoft.bigfox.io.message.user.tags.Tags;

/**
 *
 * @author QuanPH
 */
@Message(tag = Tags.CS_BIGDATA, name = "CS_BIGDATA")
public class CSBigData extends MessageOut{

    @Property(name = "data")
    private byte[] data;

    public CSBigData(byte[] data) {
        this.data = data;
    }

    
}
