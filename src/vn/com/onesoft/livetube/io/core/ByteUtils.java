package vn.com.onesoft.livetube.io.core;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;


public class ByteUtils {

	public static int getInt(byte a0, byte a1, byte a2, byte a3) {
		return ((a0 & 0xff) << 24 | (a1 & 0xff) << 16 | (a2 & 0xff) << 8 | (a3 & 0xff));
	}

	public static short getShort(byte a0, byte a1) {
		return (short) ((a0 & 0xff) << 8 | (a1 & 0xff));
	}

	public static byte[] toBytes(int a) {
		int numBytes = 4;
		byte[] result = new byte[numBytes];
		for (int i = 0; i < numBytes; i++) {
			result[i] = (byte) ((a >> (8 * (numBytes - 1 - i))) & 0x000000ff);
		}
		return result;
	}

	public static byte[] toBytes(long a) {
		int numBytes = 8;
		byte[] result = new byte[numBytes];
		for (int i = 0; i < numBytes; i++) {
			result[i] = (byte) ((a >> (8 * (numBytes - 1 - i))) & 0x000000ff);
		}
		return result;
	}

	public static byte[] toBytes(short a) {
		int numBytes = 2;
		byte[] result = new byte[numBytes];
		for (int i = 0; i < numBytes; i++) {
			result[i] = (byte) ((a >> (8 * (numBytes - 1 - i))) & 0x000000ff);
		}
		return result;
	}

	public static byte[] compress(byte[] data) {

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        GZIPOutputStream gzip;
        try {
            gzip = new GZIPOutputStream(bos);
            gzip.write(data);
            gzip.close();
        } catch (IOException ex) {
            
        }

        byte[] compressedData = bos.toByteArray();
        return compressedData;
    }

    public static byte[] deCompress(byte[] compressedData) {
        byte[] data = null;
//        try {
//            data = GZIP.inflate(compressedData);
//        } catch (IOException ex) {
//            
//        }

        return data;
    }

}
