/*
 * Author: QuanPH
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */

package vn.com.onesoft.bigfox.io.message.core.cs;

import io.netty.channel.Channel;
import vn.com.onesoft.bigfox.io.core.session.ConnectionManager;
import vn.com.onesoft.bigfox.io.message.annotations.Message;
import vn.com.onesoft.bigfox.io.message.annotations.Property;
import vn.com.onesoft.bigfox.io.message.base.MessageOut;
import vn.com.onesoft.bigfox.io.message.core.sc.SCPing;
import vn.com.onesoft.bigfox.io.message.core.tags.CoreTags;

/**
 *
 * @author QuanPH
 */
@Message(tag = CoreTags.CS_PING, name = "CS_PING", isCore = true)
public class CSPing extends MessageOut {
    
    @Property(name = "clientTime")
    private long clientTime;

    
}
