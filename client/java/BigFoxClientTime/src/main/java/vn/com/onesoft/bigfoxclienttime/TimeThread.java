/*
 * Author: QuanPH
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */
package vn.com.onesoft.bigfoxclienttime;

import java.util.logging.Level;
import java.util.logging.Logger;
import vn.com.onesoft.bigfox.io.core.session.ConnectionManager;
import vn.com.onesoft.time.client.io.message.user.cs.CSGetTime;

/**
 *
 * @author QuanPH
 */
public class TimeThread extends Thread {

    @Override
    public void run() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (true) {
            ConnectionManager.getInstance().write(new CSGetTime());
            try {
                Thread.sleep(5000);
            } catch (Exception ex) {

            }
        }
    }
}
