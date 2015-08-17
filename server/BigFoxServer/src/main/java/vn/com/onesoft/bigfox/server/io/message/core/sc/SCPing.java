package vn.com.onesoft.bigfox.server.io.message.core.sc;

/*
 * Author: HuongNS
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */


import vn.com.onesoft.bigfox.server.io.message.core.MessageOut;
import vn.com.onesoft.bigfox.server.io.message.core.Tags;
import vn.com.onesoft.bigfox.server.io.message.core.annotations.Message;
import vn.com.onesoft.bigfox.server.io.message.core.annotations.Property;

/**
 *
 * @author HuongNS
 */
@Message(tag = Tags.SC_PING, name = "SC_PING", isCore = true)
public class SCPing extends MessageOut {

    @Property(name = "serverTime")
    private long serverTime;

    public SCPing() {
        this.serverTime = System.currentTimeMillis();
    }

}
