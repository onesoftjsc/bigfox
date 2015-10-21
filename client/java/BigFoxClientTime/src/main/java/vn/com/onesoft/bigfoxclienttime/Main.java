/*
 * Author: QuanPH
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */

package vn.com.onesoft.bigfoxclienttime;

import vn.com.onesoft.bigfox.io.core.session.ConnectionManager;

/**
 *
 * @author QuanPH
 */
public class Main {
    public static void main(String[] args) throws Exception {
                TimeThread tt = new TimeThread();
        tt.start();
        ConnectionManager.getInstance().init();

    }
}
