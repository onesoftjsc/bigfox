/*
 * Author: QuanPH
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */
package vn.com.onesoft.bigfox.server.io.core.business.zone;

import io.netty.channel.Channel;
import vn.com.onesoft.bigfox.server.io.core.business.session.IBFSession;
import vn.com.onesoft.bigfox.server.io.message.base.MessageIn;
import vn.com.onesoft.bigfox.server.io.message.base.MessageOut;

/**
 *
 * @author QuanPH
 */
public interface IBFZone {

    /**
     * Start Zone
     * @throws Exception 
     */
    public void start() throws Exception;

    /**
     * Stop Zone
     */
    public void stop();

    /**
     * Restart Zone
     * @throws Exception 
     */
    public void restart() throws Exception;

    /**
     * Get  the last class loader for this zone
     * @return the last class loader for this zone
     */
    public BFClassLoaderZone getClassLoader();

    /**
     * get a session by the given sessionId
     * @param sessionId
     * @return the session associated with the given sessionId
     */
    public IBFSession getSession(String sessionId);

    /**
     * get a session by the given channel
     * @param channel
     * @return the session associated with the given channel
     */
    public IBFSession getSession(Channel channel);

    /**
     * add the given session to the zone
     * @param session 
     */
    public void addSession(IBFSession session);

    /**
     * get  MessageIn with the given tag
     * @param tag
     * @return 
     */
    public MessageIn getMessage(int tag);

    /**
     * Send a message to all users in the zone
     * @param mOut 
     */
    public void sendMessageToAll(MessageOut mOut);

    /**
     * call to reload the changed classes
     * @throws Exception 
     */
    public void reloadFilesChanged() throws Exception;

    /**
     * return the name of Zone (for example: LiveTube)
     * @return 
     */
    public String getSimpleName();

    /**
     * return the full path of the zone (example: /home/onesoft/bigone/bin/LiveTube)
     * @return 
     */
    public String getAbsolutePath();

    /**
     * set the folder from that it will monitor the changed class to reload
     * @param folderPath 
     */
    public void setMonitorFolder(String folderPath);
    
    /**
     * set the timeout for all session of the zone, if not set then sessionTimeout = default value 300s
     * @param sessionTimeout 
     */
    public void setSessionTimeout(int sessionTimeout);
    
    /**
     * get the session timeout
     * @return 
     */
    public int getSessionTimeout();

    public void setPingPeriod(int pingPeriod);
    
    public void setReconnectRetries(int timesRetriesToReconnect);
    
    public int getPingPeriod();
    
    public int getReconnectRetries();
}
