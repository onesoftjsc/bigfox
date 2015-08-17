package vn.com.onesoft.bigfox.server.io.message.core.sc;

/*
 * Author: HuongNS
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */


import vn.com.onesoft.bigfox.server.io.messaannotationsons.Property;
import vn.com.onesoft.bigfox.server.io.message.annotations.Message;
import vn.com.onesoft.bigfox.server.io.message.base.MessageOut;
import vn.com.onesoft.bigfox.server.io.message.core.tags.CoreTags;

/**
 *
 * @author HuongNS
 */
@Message(tag = CoreTags.SC_PING, name = "SC_PING", isCore = true)
public class SCPing extends MessageOut {

    @Property(name = "serverTime")
    private long serverTime;

    public SCPing() {
        this.serverTime = System.currentTimeMillis();
    }

}
