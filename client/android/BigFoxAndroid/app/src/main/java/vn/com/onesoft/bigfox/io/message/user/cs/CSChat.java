/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vn.com.onesoft.bigfox.io.message.user.cs;

import vn.com.onesoft.bigfox.io.message.BaseMessage;
import vn.com.onesoft.bigfox.io.message.annotations.Message;
import vn.com.onesoft.bigfox.io.message.annotations.Property;
import vn.com.onesoft.bigfox.io.message.user.tags.Tags;

/**
 *
 * @author phamquan
 */
@Message(tag = Tags.CS_CHAT, name = "CS_CHAT")
public class CSChat extends BaseMessage {

    @Property(name = "msg")
    private String msg;

    public CSChat(String msg) {
        this.msg = msg;
    }

    
    
}
