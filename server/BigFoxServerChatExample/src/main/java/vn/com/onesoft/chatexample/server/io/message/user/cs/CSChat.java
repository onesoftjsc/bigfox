/*
 * Author: HuongNS
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */
package vn.com.onesoft.chatexample.server.io.message.user.cs;

import java.util.Calendar;
import vn.com.onesoft.bigfox.server.io.core.business.session.BFSessionManager;
import vn.com.onesoft.bigfox.server.io.core.business.session.IBFSession;
import vn.com.onesoft.chatexample.server.io.message.user.sc.SCChat;
import vn.com.onesoft.bigfox.server.io.message.annotations.Message;
import vn.com.onesoft.bigfox.server.io.message.annotations.Property;
import vn.com.onesoft.bigfox.server.io.message.base.BFLogger;
import vn.com.onesoft.bigfox.server.io.message.base.MessageIn;
import vn.com.onesoft.chatexample.main.Main;
import vn.com.onesoft.chatexample.server.io.message.user.tags.Tags;


/**
 *
 * @author HuongNS
 */
@Message(tag = Tags.CS_CHAT, name = "CS_CHAT")
public class CSChat extends MessageIn {

    @Property(name = "msg")
    private String msg;

    @Override
    public void execute() {
         BFLogger.getInstance().info("ClassLoader CSChat " + this.getClass().getClassLoader());
         
        IBFSession session = BFSessionManager.getInstance().getSessionByChannel(this.getBFSession().getChannel());
        String name = Main.mapSessionToName.get(session);
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int mi = Calendar.getInstance().get(Calendar.MINUTE);
        int sec = Calendar.getInstance().get(Calendar.SECOND);
        String time = "" + hour + ":" + mi + ":" + sec;
        sendMessageToAll(new SCChat(time + "c\n" + name + ": "+  msg));


    }

}
