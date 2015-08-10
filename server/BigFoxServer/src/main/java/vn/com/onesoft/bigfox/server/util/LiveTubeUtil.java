/*
 * Author: HuongNS
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */
package vn.com.onesoft.bigfox.server.util;

/**
 *
 * @author HuongNS
 */
public class LiveTubeUtil {

    public static String getRandomId(int n) {
        String str = "";
        long r;

        for (int i = 0; i < n; i++) {
            r = Math.round(Math.random() * 9);
            str += r;
        }

        return str;
    }
}
