/*
 * Author: HuongNS
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */
package vn.com.onesoft.bigfox.server.io.message.sc;

import vn.com.onesoft.bigfox.server.io.message.core.MessageOut;
import vn.com.onesoft.bigfox.server.io.message.core.Tags;
import vn.com.onesoft.bigfox.server.io.message.core.annotations.Message;
import vn.com.onesoft.bigfox.server.io.message.core.annotations.Property;

/**
 *
 * @author HuongNS
 */
@Message(tag = Tags.SC_CHAT, name = "SC_CHAT")
public class SCChat extends MessageOut {

    @Property(name = "msg")
    private final String msg;

    public SCChat(String msg) {
        super();
        this.msg = msg;
    }

    @Override
    public SCChat clone(){
        return new SCChat(msg);
    }
}
