/*
 * Author: HuongNS
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */
package vn.com.onesoft.livetube.io.message.cs;

import io.netty.channel.Channel;
import vn.com.onesoft.livetube.io.message.core.MessageIn;
import vn.com.onesoft.livetube.io.message.core.Tags;
import vn.com.onesoft.livetube.io.message.core.annotations.Message;
import vn.com.onesoft.livetube.io.message.sc.SCLogout;
import vn.com.onesoft.livetube.io.session.BFSessionManager;
import vn.com.onesoft.livetube.io.session.IBFSession;

import vn.com.onesoft.livetube.main.Main;

/**
 *
 * @author HuongNS
 */
@Message(tag = Tags.CS_LOGOUT, name = "CS_LOGOUT")
public class CSLogout extends MessageIn {

    @Override
    public void execute(Channel channel) {
        Main.logger.info(this.getClass().getName());

        IBFSession sessionByChannel = BFSessionManager.getInstance().getSessionByChannel(channel);
//        BFSessionManager.getInstance().getSessionsByUserId(sessionByChannel.getdBUser().getId()).remove(sessionByChannel);
        sessionByChannel.setdBUser(null);

        BFSessionManager.getInstance().sendMessage(channel, new SCLogout());
    }
}
