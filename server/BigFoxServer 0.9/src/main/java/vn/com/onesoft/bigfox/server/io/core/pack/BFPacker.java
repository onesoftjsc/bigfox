/*
 * Author: QuanPH
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */
package vn.com.onesoft.bigfox.server.io.core.pack;

import com.google.common.collect.MapMaker;
import io.netty.channel.Channel;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import vn.com.onesoft.bigfox.server.io.message.base.BaseMessage;

/**
 *
 * @author QuanPH
 */
public class BFPacker {

    static Map<Channel, byte[]> mapChannelToByteBuffer = new MapMaker().makeMap();

    public static int MAX_LENGTH = 100000;

    public static byte[] pack(Channel channel, byte[] data) {
        byte[] buf = mapChannelToByteBuffer.get(channel);
        if (buf == null) {
            buf = new byte[0];
        }
        byte[] result = new byte[buf.length + data.length];
        System.arraycopy(buf, 0, result, 0, buf.length);
        System.arraycopy(data, 0, result, buf.length, data.length);

        int status = (int) (((data[16] & 0xff) << 24) | ((data[17] & 0xff) << 16) | ((data[18] & 0xff) << 8) | ((data[19] & 0xff)));
        if ((status & BaseMessage.STATUS_CONTINUE) == 0) {
            int length = result.length; // 4 byte length, 4 byte tag
            result[0] = (byte) ((length >> 24) & 0x00ff);
            result[1] = (byte) ((length >> 16) & 0x00ff);
            result[2] = (byte) ((length >> 8) & 0x00ff);
            result[3] = (byte) ((length) & 0x00ff);
            if (length > 10000){
                int a = 0;
            }
            mapChannelToByteBuffer.remove(channel);
            return result;
        } else {
            mapChannelToByteBuffer.put(channel, result);
            return null;
        }
    }

    public static ArrayList<byte[]> slide(byte[] data) {
        if (data.length <= MAX_LENGTH) {
            return new ArrayList<>(Arrays.asList(data));
        } else {
            ArrayList<byte[]> result = new ArrayList();
            for (int i = 0; i < data.length / MAX_LENGTH; i++) {
                byte[] idata = new byte[MAX_LENGTH];
                System.arraycopy(data, i, idata, 0, MAX_LENGTH);
                normalData(BaseMessage.STATUS_CONTINUE, idata);
                result.add(idata);
            }

            if (data.length > MAX_LENGTH * (data.length / MAX_LENGTH)) {
                int len = data.length - MAX_LENGTH * (data.length / MAX_LENGTH);
                byte[] idata = new byte[len];
                System.arraycopy(data, data.length - len, idata, 0, len);
                normalData(0, idata);
                result.add(idata);
            }

            return result;
        }
    }

    private static void normalData(int status, byte[] data) {
        int length = data.length; // 4 byte length, 4 byte tag
        data[0] = (byte) ((length >> 24) & 0x00ff);
        data[1] = (byte) ((length >> 16) & 0x00ff);
        data[2] = (byte) ((length >> 8) & 0x00ff);
        data[3] = (byte) ((length) & 0x00ff);

        data[16] = (byte) ((status >> 24) & 0x00ff);
        data[17] = (byte) ((status >> 16) & 0x00ff);
        data[18] = (byte) ((status >> 8) & 0x00ff);
        data[19] = (byte) ((status) & 0x00ff);
    }
}
