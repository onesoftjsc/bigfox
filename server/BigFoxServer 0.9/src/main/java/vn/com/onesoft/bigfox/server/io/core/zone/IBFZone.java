/*
 * Author: QuanPH
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */

package vn.com.onesoft.bigfox.server.io.core.zone;

import io.netty.channel.Channel;
import vn.com.onesoft.bigfox.server.io.core.session.IBFSession;
import vn.com.onesoft.bigfox.server.io.message.base.MessageIn;
import vn.com.onesoft.bigfox.server.io.message.base.MessageOut;

/**
 *
 * @author QuanPH
 */
public interface IBFZone {
    public void start() throws Exception;
    public void stop();
    public void restart() throws Exception;

    public void onMessage(Channel channel, byte[] data);
    public BFClassLoaderZone getClassLoader();
    public IBFSession getSession(String sessionId);
    public IBFSession getSession(Channel channel);
    public void addSession(IBFSession session);
    public String getSimpleName();
    public MessageIn getMessage(int tag);

    public void sendMessageToAll(MessageOut mOut);
     public void reloadFilesChanged() throws Exception;
     public String getAbsolutePath();
     public void setMonitorFolder(String folderPath);
}
