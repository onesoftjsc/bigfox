/*
 * Author: QuanPH
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */

package vn.com.onesoft.bigfox.io.core.compress;

/**
 *
 * @author QuanPH
 */
public interface ICompress {
    public byte[] compress(byte[] data);
    public byte[] decompress(byte[] data);
}
