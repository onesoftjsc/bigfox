/*
 * Author: QuanPH
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */
package vn.com.onesoft.bigfox.io.message.core.sc;

import vn.com.onesoft.bigfox.io.message.base.BaseMessage;
import vn.com.onesoft.bigfox.io.message.base.IMessageIn;
import vn.com.onesoft.bigfox.io.message.core.tags.CoreTags;
import vn.com.onesoft.bigfox.io.message.annotations.Message;
import vn.com.onesoft.bigfox.io.message.annotations.Property;

/**
 *
 * @author QuanPH
 */
@Message(tag = CoreTags.SC_PING, name = "SC_PING", isCore = true)
public class SCPing extends BaseMessage implements IMessageIn {

    @Property(name = "serverTime")
    private long serverTime;

    @Override
    public void execute() {

    }

}
