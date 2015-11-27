/*
 * Author: QuanPH
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */
package vn.com.onesoft.bigfox.server.io.core.data.encrypt;

import com.google.common.collect.MapMaker;
import io.netty.channel.Channel;
import java.util.Map;

/**
 *
 * @author QuanPH
 */
public class BFEncryptManager {

    public static Map<Channel, Integer> mapChannelToValidationCode = new MapMaker().makeMap();

    public static byte[] crypt(Channel channel, byte[] data) {
        int validationCode = mapChannelToValidationCode.get(channel);
        for (int i = 24; i < data.length; i++) {
            data[i] = (byte) ((data[i] ^ validationCode) & 0x00ff);
        }
        return data;
    }

}
