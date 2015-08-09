
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
@Message(tag = Tags.CS_SETTING, name = "CS_SETTING")
public class CSSetting extends MessageIn {

    @Property(name = "notificationSubcription")
    public boolean notificationSubcription;
    @Property(name = "notificationSchedule")
    public boolean notificationSchedule;
    @Property(name = "notificationReminder")
    public boolean notificationReminder;

    @Override
    public void execute(Channel channel) {
        Main.logger.info(this.getClass().getName());
        if (BFSessionManager.getInstance().getSessionByChannel(channel).getdBUser() == null) {
            return;
        }
        BFSessionManager.getInstance().sendMessage(channel, null);

    }

}
