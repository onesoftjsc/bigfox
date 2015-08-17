/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.com.onesoft.bigfox.io.message.cs;

import vn.com.onesoft.bigfox.io.message.core.BaseMessage;
import vn.com.onesoft.bigfox.io.message.core.Tags;
import vn.com.onesoft.bigfox.io.message.core.annotations.Message;
import vn.com.onesoft.bigfox.io.message.core.annotations.Property;

/**
 *
 * @author phamquan
 */
@Message(tag = Tags.CS_PING, name = "CS_PING", isCore = true)
public class CSPing extends BaseMessage {

    @Property(name = "clientTime")
    private long clientTime;

    public CSPing() {
        this.clientTime = System.currentTimeMillis();
    }
    
    
}
