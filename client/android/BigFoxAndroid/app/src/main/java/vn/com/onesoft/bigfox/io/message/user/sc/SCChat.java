/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.com.onesoft.bigfox.io.message.user.sc;

import vn.com.onesoft.bigfox.MainActivity;
import vn.com.onesoft.bigfox.io.message.base.BaseMessage;
import vn.com.onesoft.bigfox.io.message.base.IMessageIn;
import vn.com.onesoft.bigfox.io.message.annotations.Message;
import vn.com.onesoft.bigfox.io.message.annotations.Property;
import vn.com.onesoft.bigfox.io.message.user.tags.Tags;

/**
 *
 * @author phamquan
 */
@Message(tag = Tags.SC_CHAT, name = "SC_CHAT")
public class SCChat extends BaseMessage implements IMessageIn {

    @Property(name = "msg")
    private String msg;

    @Override
    public void execute() {
          MainActivity.getInstance().receiveChat(msg);
    }

}
