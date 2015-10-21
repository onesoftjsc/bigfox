/*
 * Author: QuanPH
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */

package vn.com.onesoft.time.server.io.message.user.sc;

import java.text.SimpleDateFormat;
import java.util.Date;
import vn.com.onesoft.bigfox.server.io.message.annotations.Message;
import vn.com.onesoft.bigfox.server.io.message.annotations.Property;
import vn.com.onesoft.bigfox.server.io.message.base.MessageOut;
import vn.com.onesoft.time.server.io.message.user.tags.Tags;

/**
 *
 * @author QuanPH
 */
@Message(tag = Tags.SC_GET_TIME, name = "SC_GET_TIME")
public class SCGetTime extends MessageOut{

    @Property(name = "serverTime")
    private String serverTime;
    
    public SCGetTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        Date date = new Date();
        serverTime = sdf.format(date.getTime());
    }
    
}
