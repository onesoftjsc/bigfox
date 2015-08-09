
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
import vn.com.onesoft.livetube.io.message.core.annotations.Property;
import vn.com.onesoft.livetube.io.session.BFSessionManager;
import vn.com.onesoft.livetube.main.Main;

/**
 *
 * @author HuongNS
 */
@Message(tag = Tags.CS_SET_REMINDER, name = "CS_SET_REMINDER")
public class CSSetReminder extends MessageIn {

    @Property(name = "videoId")
    public int videoId;
    // action = 1: set reminder, action = 0: unset reminder
    @Property(name = "action")
    public int action;
    public static final int ACTION_SET = 1;
    public static final int ACTION_UNSET = 0;

    @Override
    public void execute(Channel channel) {
        Main.logger.info(this.getClass().getName());
        if (BFSessionManager.getInstance().getSessionByChannel(channel).getdBUser() == null) {
            return;
        }

        BFSessionManager.getInstance().sendMessage(channel, null);

    }

}
