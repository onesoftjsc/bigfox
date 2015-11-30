/*
 * Author: QuanPH
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */
package vn.com.onesoft.bigfox.io.core.session;

import vn.com.onesoft.bigfox.io.message.core.cs.CSPing;

/**
 *
 * @author QuanPH
 */
public class PingThreadManager {

    private long idConnection = 0;

    private static PingThreadManager _instance;
    public static int timeSleep = 5; //s
    
    public static PingThreadManager getInstance() {
        if (_instance == null) {
            _instance = new PingThreadManager();
            _instance.start();
        }
        return _instance;
    }

    private void start() {
        new Thread(new Runnable() {

            public void run() {
                while (true) {
                    ConnectionManager.getInstance().write(new CSPing());

                    try {
                        Thread.sleep(timeSleep * 1000);
                    } catch (InterruptedException e) {

                    }
                }
            }
        }).start();
    }

}
