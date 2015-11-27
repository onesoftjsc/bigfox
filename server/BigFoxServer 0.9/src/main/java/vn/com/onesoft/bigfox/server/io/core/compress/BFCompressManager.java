/*
 * Author: QuanPH
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */
package vn.com.onesoft.bigfox.server.io.core.compress;

import vn.com.onesoft.bigfox.server.main.BFConfig;

/**
 *
 * @author QuanPH
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
        if (BFConfig.getInstance().enableZip()) {
            return cp.compress(data);
        } else {
            return data;
        }
    }

    public byte[] decompress(byte[] data) {
        return cp.decompress(data);
    }
}
