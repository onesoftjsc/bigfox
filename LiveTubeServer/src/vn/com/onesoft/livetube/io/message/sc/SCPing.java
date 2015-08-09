/*
 * Author: HuongNS
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */
package vn.com.onesoft.livetube.io.message.sc;

import vn.com.onesoft.livetube.io.message.core.MessageOut;
import vn.com.onesoft.livetube.io.message.core.Tags;
import vn.com.onesoft.livetube.io.message.core.annotations.Message;
import vn.com.onesoft.livetube.io.message.core.annotations.Property;

/**
 *
 * @author HuongNS
 */
@Message(tag = Tags.SC_PING, name = "SC_PING")
public class SCPing extends MessageOut {

    @Property(name = "serverTime")
    private long serverTime;

    public SCPing() {
        this.serverTime = System.currentTimeMillis();
    }

}
