/*
 * Author: QuanPH
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */
package vn.com.onesoft.bigfox.server.io.core.session;

import io.netty.channel.Channel;
import vn.com.onesoft.bigfox.server.io.core.message.base.MessageIn;
import vn.com.onesoft.bigfox.server.io.core.message.base.MessageOut;
import vn.com.onesoft.bigfox.server.io.core.objects.message.ClientInfo;


/**
 *
 * @author QuanPH
 */
public interface IBFSession {

    public Channel getChannel();

    public void setChannel(Channel channel);

    public String getSessionId();

    public void setSessionId(String sessionId);

    public BFSessionType getSessionType();

    public void setSessionType(BFSessionType sessionType);

    public String getIp();

    public void destroy();

    public int getValidationCode();

    public int getSSequence();

    public void setSSequence(int sSequence);

    public int getMSequence();

    public void setMSequence(int mSequence);

    public void setNewChannel(Channel channel, ClientInfo clientInfo);

    public void setClientInfo(ClientInfo clientInfo);

    public ClientInfo getClientInfo();

    public void putOutMessageOnQueue(MessageOut mOut);

    public void cleanOutMessageQueue(MessageIn mIn);

    public void reSendMessageFromQueue();

    public long getLastTimeReceive();

    public void setLastTimeReceive(long lastTimeReceive);


    public void onTimeout();

    public void onChannelClose(Channel channel);
}
