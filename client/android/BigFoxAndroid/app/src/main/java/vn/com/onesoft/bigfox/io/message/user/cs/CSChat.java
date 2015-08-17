/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vn.com.onesoft.bigfox.io.message.user.cs;

import vn.com.onesoft.bigfox.io.message.core.base.BaseMessage;
import vn.com.onesoft.bigfox.io.message.core.base.CoreTags;
import vn.com.onesoft.bigfox.io.message.core.annotations.Message;
import vn.com.onesoft.bigfox.io.message.core.annotations.Property;
import vn.com.onesoft.bigfox.io.message.user.Tags;

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
