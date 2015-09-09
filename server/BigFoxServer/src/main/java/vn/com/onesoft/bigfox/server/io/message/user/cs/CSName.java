/*
 * Author: QuanPH
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */

package vn.com.onesoft.bigfox.server.io.message.user.cs;

import io.netty.channel.Channel;
import vn.com.onesoft.bigfox.server.io.core.session.BFSessionManager;
import vn.com.onesoft.bigfox.server.io.core.session.IBFSession;
import vn.com.onesoft.bigfox.server.io.messaannotationsons.Property;
import vn.com.onesoft.bigfox.server.io.message.annotations.Message;
import vn.com.onesoft.bigfox.server.io.message.base.MessageIn;
import vn.com.onesoft.bigfox.server.io.message.user.tags.Tags;
import vn.com.onesoft.bigfox.server.main.Main;

/**
 *
 * @author QuanPH
 */
@Message(tag = Tags.CS_NAME, name = "CS_NAME")
public class CSName extends MessageIn {

    @Property(name = "msg")
    private String msg;

    

    @Override
    public void execute(Channel channel) {
        IBFSession session = BFSessionManager.getInstance().getSessionByChannel(channel);
        Main.mapSessionToName.put(session, msg);
    }



}
