/*
 * Author: QuanPH
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */
package vn.com.onesoft.bigfox.server.main;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.security.SecureRandom;

/**
 *
 * @author QuanPH
 */
public class BFUtils {

    private static SecureRandom random = new SecureRandom();

    public static long getCurrentTime() {
        return System.currentTimeMillis();
    }

    public static void sendSMSCode(String mobile, String code) {
        Socket socket = null;
        InputStream is = null;
        OutputStream os = null;
        try {
            socket = new Socket("123.30.186.235", 23450);
            is = socket.getInputStream();
            os = socket.getOutputStream();

            byte[] bytes = new byte[1000];
            is.read(bytes);
            String nick = new String(bytes);
            os.write(("bigone" + "\n").getBytes());
            os.flush();

            is.read(bytes);
            String pass = new String(bytes);
            os.write(("onesoft" + "\n").getBytes());
            os.flush();

            is.read(bytes);
            String welcome = new String(bytes);

            os.write(("sendSMS " + mobile + " " + code + "\n").getBytes());
            os.flush();

        } catch (Exception ex) {
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException ex) {
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ex) {
                }
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException ex) {
                }
            }
        }
    }
}
