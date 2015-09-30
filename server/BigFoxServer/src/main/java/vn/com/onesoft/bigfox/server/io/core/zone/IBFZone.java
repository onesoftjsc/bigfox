/*
 * Author: QuanPH
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */

package vn.com.onesoft.bigfox.server.io.core.zone;

import io.netty.channel.Channel;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import vn.com.onesoft.bigfox.server.io.core.session.IBFSession;
import vn.com.onesoft.bigfox.server.io.message.base.MessageIn;
import vn.com.onesoft.bigfox.server.io.message.base.MessageOut;

/**
 *
 * @author QuanPH
 */
public interface IBFZone {
    public void start();
    public void stop();
    public void restart();

    public void onMessage(Channel channel, byte[] data);
    public BFClassLoader getClassLoader();
    public IBFSession getSession(String sessionId);
    public IBFSession getSession(Channel channel);
    public void addSession(IBFSession session);
    public String getSimpleName();
    public MessageIn getMessage(int tag);
    public void loadZone() throws Exception;
    public void sendMessageToAll(MessageOut mOut);
     public void reloadFilesChanged() throws Exception;
}
