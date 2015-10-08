/*
 * Author: QuanPH
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */

package vn.com.onesoft.bigfox.server.example.chat;

import vn.com.onesoft.bigfox.server.io.core.session.IBFSession;
import vn.com.onesoft.bigfox.server.io.core.session.IBFSessionEvent;

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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
