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
public class BFSessionTick implements Runnable{

    private IBFSession session;
    public BFSessionTick(IBFSession session) {
        this.session = session;
    }

    
    @Override
    public void run() {
        BFSessionManager.getInstance().getSessionEvent().onDelay(session, (int) (System.currentTimeMillis() - session.getLastTimeReceive()) / 1000);
    }
    
}
