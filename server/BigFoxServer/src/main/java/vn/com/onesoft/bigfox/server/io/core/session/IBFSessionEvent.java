/*
 * Author: QuanPH
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */

package vn.com.onesoft.bigfox.server.io.core.session;

/**
 *
 * @author QuanPH
 */
public interface IBFSessionEvent {
    public void startSession(IBFSession session);
    public void removeSession(IBFSession session);
}
