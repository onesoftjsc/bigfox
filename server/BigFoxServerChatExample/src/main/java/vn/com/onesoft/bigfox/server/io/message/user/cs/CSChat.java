/*
 * Author: HuongNS
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */
package vn.com.onesoft.bigfox.server.io.message.user.cs;

import io.netty.channel.Channel;
import java.util.Calendar;
import vn.com.onesoft.bigfox.server.io.message.user.sc.SCChat;
import vn.com.onesoft.bigfox.server.io.core.session.BFSessionManager;
import vn.com.onesoft.bigfox.server.io.core.session.IBFSession;
import vn.com.onesoft.bigfox.server.io.core.zone.BFZoneManager;
import vn.com.onesoft.bigfox.server.io.core.zone.IBFZone;
import vn.com.onesoft.bigfox.server.io.messaannotationsons.Property;
import vn.com.onesoft.bigfox.server.io.message.annotations.Message;
import vn.com.onesoft.bigfox.server.io.message.base.BFLogger;
import vn.com.onesoft.bigfox.server.io.message.base.MessageIn;
import vn.com.onesoft.bigfox.server.io.message.user.tags.Tags;
import vn.com.onesoft.bigfox.server.main.Main;

/**
 *
 * @author HuongNS
 */
@Message(tag = Tags.CS_CHAT, name = "CS_CHAT")
public class CSChat extends MessageIn {

    @Property(name = "msg")
    private String msg;

    @Override
    public void execute(Channel channel) {
        IBFSession session = BFSessionManager.getInstance().getSessionByChannel(channel);
        String name = Main.mapSessionToName.get(session);
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int mi = Calendar.getInstance().get(Calendar.MINUTE);
        int sec = Calendar.getInstance().get(Calendar.SECOND);
        String time = "" + hour + ":" + mi + ":" + sec;
        IBFZone zone = BFZoneManager.getInstance().getZone(channel);
        
        zone.sendMessageToAll(new SCChat(time + "\n" + name + ": "+  msg));

    }

}
