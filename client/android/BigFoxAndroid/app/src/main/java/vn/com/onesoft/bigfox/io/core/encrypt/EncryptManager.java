package vn.com.onesoft.bigfox.io.core.encrypt;

/**
 * Created by phamquan on 8/18/15.
 */

import vn.com.onesoft.bigfox.io.core.session.ConnectionManager;

/**
 * @author QuanPH
 */
public class EncryptManager {

    public static byte[] crypt(byte[] data) {
        int validationCode = ConnectionManager.getInstance().validationCode;
        for (int i = 24; i < data.length; i++) {
            data[i] = (byte) ((data[i] ^ validationCode) & 0x00ff);
        }
        return data;
    }

}
