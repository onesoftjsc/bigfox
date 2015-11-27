/*
 * Author: QuanPH
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */

package vn.com.onesoft.chatexample.main;

import vn.com.onesoft.bigfox.server.io.core.business.session.IBFSession;
import vn.com.onesoft.bigfox.server.io.core.business.session.IBFSessionEvent;
import vn.com.onesoft.bigfox.server.io.message.base.BFLogger;

/**
 *
 * @author QuanPH
 */
public class BFSessionEvent implements IBFSessionEvent{

    @Override
    public void startSession(IBFSession session) {
        
    }

    @Override
    public void reconnectSession(IBFSession session) {
        
    }

    @Override
    public void removeSession(IBFSession session) {
        
    }

    @Override
    public void closeChannel(IBFSession session) {
        
    }

    @Override
    public void onDelay(IBFSession session, int delaySecond) {
        BFLogger.getInstance().info("onDelay " + delaySecond);
    }
    
}
