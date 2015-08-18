/*
 * Author: HuongNS
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */
package vn.com.onesoft.bigfox.server.io.message.user.sc;

import vn.com.onesoft.bigfox.server.io.messaannotationsons.Property;
import vn.com.onesoft.bigfox.server.io.message.annotations.Message;
import vn.com.onesoft.bigfox.server.io.message.base.MessageOut;
import vn.com.onesoft.bigfox.server.io.message.user.tags.Tags;

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
