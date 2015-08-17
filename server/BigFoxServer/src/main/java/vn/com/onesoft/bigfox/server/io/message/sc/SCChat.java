/*
 * Author: HuongNS
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */
package vn.com.onesoft.bigfox.server.io.message.sc;

import vn.com.onesoft.bigfox.server.io.core.annotat.messageions.Message;
import vn.com.onesoft.bigfox.server.io.core.annotat.messageions.Property;
import vn.com.onesoft.bigfox.server.io.core.message.base.MessageOut;
import vn.com.onesoft.bigfox.server.io.core.message.tags.CoreTags;

/**
 *
 * @author HuongNS
 */
@Message(tag = CoreTags.SC_CHAT, name = "SC_CHAT")
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
