/*
 * Author: QuanPH
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */
package vn.com.onesoft.bigfox.io.core.compress;



/**
 *
 * @author QuanPHi
 */
public class BFCompressManager {

    private ICompress cp;

    private static BFCompressManager _instance = null;

    public static BFCompressManager getInstance() {
        if (_instance == null) {
            _instance = new BFCompressManager();
            _instance.setCompress(new BFZip());
        }
        return _instance;
    }

    private void setCompress(ICompress compress) {
        this.cp = compress;
    }

    public byte[] compress(byte[] data) {
            return cp.compress(data);
    }

    public byte[] decompress(byte[] data) {
        return cp.decompress(data);
    }
}
