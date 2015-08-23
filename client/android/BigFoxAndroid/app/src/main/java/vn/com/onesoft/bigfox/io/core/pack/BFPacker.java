package vn.com.onesoft.bigfox.io.core.pack;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

import vn.com.onesoft.bigfox.io.message.BaseMessage;

/**
 * Created by phamquan on 8/21/15.
 */
public class BFPacker {

    public static int MAX_LENGTH = 100000;

    public static Vector slide(byte[] data) {
        Vector result = new Vector();
        if (data.length <= MAX_LENGTH) {
            result.add(data);
        } else {
            byte[] header = new byte[24];
            System.arraycopy(data, 0, header, 0, 24);
            byte[] body = new byte[data.length - header.length];
            System.arraycopy(data, header.length, body, 0, data.length - header.length);

            for (int i = 0; i < body.length / MAX_LENGTH; i++) {
                byte[] idata = new byte[MAX_LENGTH + header.length];
                System.arraycopy(header, 0, idata, 0, header.length);
                System.arraycopy(body, i * MAX_LENGTH, idata, header.length, MAX_LENGTH);
                normalData(BaseMessage.STATUS_CONTINUE, idata);
                result.add(idata);
            }

            if (data.length > MAX_LENGTH * (data.length / MAX_LENGTH)) {
                int len = body.length - MAX_LENGTH * (body.length / MAX_LENGTH);
                byte[] idata = new byte[len + header.length];
                System.arraycopy(header, 0, idata, 0, header.length);
                System.arraycopy(body, body.length - len, idata, header.length, len);
                normalData(0, idata);
                result.add(idata);
            }

            return result;
        }

        return result;
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
