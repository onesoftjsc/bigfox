/*
 * Author: HuongNS
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */
package vn.com.onesoft.bigfox.io.message.core.cs;

import io.netty.channel.Channel;
import vn.com.onesoft.bigfox.io.message.annotations.Message;
import vn.com.onesoft.bigfox.io.message.annotations.Property;
import vn.com.onesoft.bigfox.io.message.base.MessageOut;
import vn.com.onesoft.bigfox.io.message.core.objects.ClientInfo;
import vn.com.onesoft.bigfox.io.message.core.tags.CoreTags;


/**
 *
 * @author HuongNS
 */
@Message(tag = CoreTags.CS_CLIENT_INFO, name = "CS_CLIENT_INFO", isCore = true)
public class CSClientInfo extends MessageOut {
    
    @Property(name = "clientInfo")
    private ClientInfo clientInfo;

}
