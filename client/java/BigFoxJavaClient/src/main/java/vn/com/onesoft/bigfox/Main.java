/*
 * Author: QuanPH
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */

package vn.com.onesoft.bigfox;

import vn.com.onesoft.bigfox.io.core.session.BFConnectionManager;

/**
 *
 * @author QuanPH
 */
public class Main {
        public static void main(String[] args) {
            BFConnectionManager.getInstance().init();
        }
}
