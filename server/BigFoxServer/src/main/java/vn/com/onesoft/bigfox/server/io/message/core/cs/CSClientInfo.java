/*
 * Author: HuongNS
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */
package vn.com.onesoft.bigfox.server.io.message.core.cs;

import io.netty.channel.Channel;
import vn.com.onesoft.bigfox.server.io.message.core.tags.CoreTags;
import vn.com.onesoft.bigfox.server.io.messaannotationsons.Property;
import vn.com.onesoft.bigfox.server.io.message.annotations.Message;
import vn.com.onesoft.bigfox.server.io.message.base.MessageIn;
import vn.com.onesoft.bigfox.server.io.message.core.objects.ClientInfo;


/**
 *
 * @author HuongNS
 */
@Message(tag = CoreTags.CS_CLIENT_INFO, name = "CS_CLIENT_INFO", isCore = true)
public class CSClientInfo extends MessageIn {
    
    @Property(name = "clientInfo")
    private ClientInfo clientInfo;
    
    @Override
    public void execute(Channel channel) {

    }

    /**
     * @return the clientInfo
     */
    public ClientInfo getClientInfo() {
        return clientInfo;

    }
}
