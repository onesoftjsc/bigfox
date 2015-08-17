/*
 * Author: HuongNS
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */
package vn.com.onesoft.bigfox.server.io.message.cs;

import io.netty.channel.Channel;
import java.util.Calendar;
import vn.com.onesoft.bigfox.server.io.core.annotat.messageions.Message;
import vn.com.onesoft.bigfox.server.io.core.annotat.messageions.Property;
import vn.com.onesoft.bigfox.server.io.core.message.base.MessageIn;
import vn.com.onesoft.bigfox.server.io.message.sc.SCChat;
import vn.com.onesoft.bigfox.server.io.core.session.BFSessionManager;
import vn.com.onesoft.bigfox.server.io.core.session.IBFSession;
import vn.com.onesoft.bigfox.server.io.core.message.tags.CoreTags;

/**
 *
 * @author HuongNS
 */
@Message(tag = CoreTags.CS_CHAT, name = "CS_CHAT")
public class CSChat extends MessageIn {

    @Property(name = "msg")
    private String msg;

    @Override
    public void execute(Channel channel) {
        IBFSession session = BFSessionManager.getInstance().getSessionByChannel(channel);
        String name = CSName.mapSessionToName.get(session);
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int mi = Calendar.getInstance().get(Calendar.MINUTE);
        int sec = Calendar.getInstance().get(Calendar.SECOND);
        String time = "" + hour + ":" + mi + ":" + sec;
        BFSessionManager.getInstance().sendToAll(new SCChat(time + "\n" + name + " : " + msg));

    }

}
