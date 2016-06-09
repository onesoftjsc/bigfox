/*
 * Author: QuanPH
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */

package vn.com.onesoft.time.client.io.message.user.cs;

import vn.com.onesoft.bigfox.io.message.annotations.Message;
import vn.com.onesoft.bigfox.io.message.annotations.Property;
import vn.com.onesoft.bigfox.io.message.base.MessageOut;
import vn.com.onesoft.time.client.io.message.user.tags.Tags;

/**
 *
 * @author QuanPH
 */
@Message(tag = Tags.CS_GET_TIME, name = "CS_GET_TIME")
public class CSGetTime extends MessageOut{

        @Property(name = "serverTime")
    private long serverTime;

    public CSGetTime(long serverTime) {
        this.serverTime = serverTime;
    }
        
        
}
