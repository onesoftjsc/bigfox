/*
 * Author: QuanPH
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */
package vn.com.onesoft.bigfox.io.core.encrypt;

import com.google.common.collect.MapMaker;
import io.netty.channel.Channel;
import java.util.Map;
import vn.com.onesoft.bigfox.io.core.session.ConnectionManager;
import vn.com.onesoft.bigfox.io.message.core.tags.CoreTags;

/**
 *
 * @author QuanPH
 */
public class BFEncryptManager {

    public static Map<Channel, Integer> mapChannelToValidationCode = new MapMaker().makeMap();

    public static byte[] crypt(byte[] data) {
        int tag = (int) (((data[4] & 0xff) << 24) | ((data[5] & 0xff) << 16) | ((data[6] & 0xff) << 8) | ((data[7] & 0xff)));
        if (tag != CoreTags.SC_VALIDATION_CODE) {
            int validationCode = ConnectionManager.getInstance().getValidationCode();
            for (int i = 24; i < data.length; i++) {
                data[i] = (byte) ((data[i] ^ validationCode) & 0x00ff);
            }
        }
        return data;
    }

}
