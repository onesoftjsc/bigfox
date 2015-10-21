/*
 * Author: QuanPH
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */
package vn.com.onesoft.bigfox.io.core.compress;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import vn.com.onesoft.bigfox.io.message.base.BaseMessage;


/**
 *
 * @author QuanPH
 */
public class BFZip implements ICompress {

    @Override
    public byte[] compress(byte[] data) {
        if (data.length <= 100) {
            return data;
        } else {
            byte[] uData = new byte[data.length - 24];
            for (int i = 0; i < uData.length; i++) {
                uData[i] = data[i + 24];
            }

            try {
                ByteArrayOutputStream os = new ByteArrayOutputStream(uData.length);
                GZIPOutputStream gos = new GZIPOutputStream(os);
                gos.write(uData);
                gos.close();
                byte[] compressed = os.toByteArray();
                os.close();

                if (compressed.length < data.length - 24) {
                    byte[] resultData = new byte[compressed.length + 24];
                    System.arraycopy(data, 0, resultData, 0, 24);
                    for (int i = 24; i < resultData.length; i++) {
                        resultData[i] = compressed[i - 24];
                    }

                    int length = resultData.length; // 4 byte length, 4 byte tag
                    resultData[0] = (byte) ((length >> 24) & 0x00ff);
                    resultData[1] = (byte) ((length >> 16) & 0x00ff);
                    resultData[2] = (byte) ((length >> 8) & 0x00ff);
                    resultData[3] = (byte) ((length) & 0x00ff);
                    resultData[19] = (byte) (resultData[19] | BaseMessage.STATUS_ZIP);

                    return resultData;
                } else {
                    return data;
                }
            } catch (Exception ex) {
                return data;
            }
        }
    }

    @Override
    public byte[] decompress(byte[] data) {
        if ((data[19] & BaseMessage.STATUS_ZIP) != BaseMessage.STATUS_ZIP) {
            return data;
        } else {
            try {
                byte[] uData = new byte[data.length - 24];
                for (int i = 0; i < uData.length; i++) {
                    uData[i] = data[i + 24];
                }

                final int BUFFER_SIZE = 32;
                ByteArrayInputStream is = new ByteArrayInputStream(uData);
                GZIPInputStream gis = new GZIPInputStream(is, BUFFER_SIZE);
                ByteArrayOutputStream bout = new ByteArrayOutputStream();

                byte[] buf = new byte[BUFFER_SIZE];
                int bytesRead;
                while ((bytesRead = gis.read(buf)) != -1) {
                    bout.write(buf, 0, bytesRead);
                }

                uData = bout.toByteArray();

                byte[] resultData = new byte[uData.length + 24];
                System.arraycopy(data, 0, resultData, 0, 24);
                for (int i = 24; i < resultData.length; i++) {
                    resultData[i] = uData[i - 24];
                }

                int length = resultData.length; // 4 byte length, 4 byte tag
                resultData[0] = (byte) ((length >> 24) & 0x00ff);
                resultData[1] = (byte) ((length >> 16) & 0x00ff);
                resultData[2] = (byte) ((length >> 8) & 0x00ff);
                resultData[3] = (byte) ((length) & 0x00ff);

                return resultData;
            } catch (Exception ex) {
                return data;
            }
        }
    }

}
