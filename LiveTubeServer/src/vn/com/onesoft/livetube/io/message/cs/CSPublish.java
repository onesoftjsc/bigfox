
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
@Message(tag = Tags.CS_PUBLISH, name = "CS_PUBLISH")
public class CSPublish extends MessageIn {

    @Property(name = "videoId")
    public int videoId;
    @Property(name = "action")
    public int action; // action = 0: publish, action =1: unpublish
    public static final int ACTION_PUBLISH = 0;
    public static final int ACTION_UNPUBLISH = 1;

    @Override
    public void execute(Channel channel) {
        Main.logger.info(this.getClass().getName());
        if (BFSessionManager.getInstance().getSessionByChannel(channel).getdBUser() == null) {
            return;
        }
    }

}
