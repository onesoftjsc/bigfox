/*
 * Author: QuanPH
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */
package vn.com.onesoft.bigfox.io.message.sc;

import vn.com.onesoft.bigfox.io.message.core.BaseMessage;
import vn.com.onesoft.bigfox.io.message.core.IMessageIn;
import vn.com.onesoft.bigfox.io.message.core.Tags;
import vn.com.onesoft.bigfox.io.message.core.annotations.Message;
import vn.com.onesoft.bigfox.io.message.core.annotations.Property;

/**
 *
 * @author QuanPH
 */
@Message(tag = Tags.SC_PING, name = "SC_PING")
public class SCPing extends BaseMessage implements IMessageIn {

    @Property(name = "serverTime")
    private long serverTime;

    @Override
    public void execute() {

    }

}
