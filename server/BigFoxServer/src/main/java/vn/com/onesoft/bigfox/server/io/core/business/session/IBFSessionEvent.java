/*
 * Author: QuanPH
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */

package vn.com.onesoft.bigfox.server.io.core.business.session;

/**
 *
 * @author QuanPH
 */
public interface IBFSessionEvent {
    public void startSession(IBFSession session);
    public void reconnectSession(IBFSession session);
    public void removeSession(IBFSession session);
     public void onDelay(IBFSession session, int delaySecond);
}
