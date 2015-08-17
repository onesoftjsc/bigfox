/*
 * Author: HuongNS
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */
package vn.com.onesoft.bigfox.server.io.core.message.cs;

import io.netty.channel.Channel;
import vn.com.onesoft.bigfox.server.io.core.annotat.messageions.Message;
import vn.com.onesoft.bigfox.server.io.core.annotat.messageions.Property;
import vn.com.onesoft.bigfox.server.io.core.message.base.MessageIn;
import vn.com.onesoft.bigfox.server.io.core.message.tags.CoreTags;
import vn.com.onesoft.bigfox.server.io.core.objects.message.ClientInfo;


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

        int a = 0;
//        Main.logger.info(this.getClass().getName());
//        Player player = new Player(channel, clientInfo);
//        LiveTubeContext.mapChannelToPlayer.put(channel, player);
    }

    /**
     * @return the clientInfo
     */
    public ClientInfo getClientInfo() {
        return clientInfo;

    }
}
