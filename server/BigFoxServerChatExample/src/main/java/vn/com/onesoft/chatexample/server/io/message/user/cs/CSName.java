/*
 * Author: QuanPH
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */

package vn.com.onesoft.chatexample.server.io.message.user.cs;

import io.netty.channel.Channel;
import vn.com.onesoft.bigfox.server.io.core.business.session.BFSessionManager;
import vn.com.onesoft.bigfox.server.io.core.business.session.IBFSession;
import vn.com.onesoft.bigfox.server.io.message.annotations.Message;
import vn.com.onesoft.bigfox.server.io.message.annotations.Property;
import vn.com.onesoft.bigfox.server.io.message.base.MessageIn;
import vn.com.onesoft.chatexample.main.Main;
import vn.com.onesoft.chatexample.server.io.message.user.tags.Tags;


/**
 *
 * @author QuanPH
 */
@Message(tag = Tags.CS_NAME, name = "CS_NAME")
public class CSName extends MessageIn {

    @Property(name = "msg")
    private String msg;

    

    @Override
    public void execute() {
        IBFSession session = BFSessionManager.getInstance().getSessionByChannel(this.getBFSession().getChannel());
        Main.mapSessionToName.put(session, msg);
        
    }



}
