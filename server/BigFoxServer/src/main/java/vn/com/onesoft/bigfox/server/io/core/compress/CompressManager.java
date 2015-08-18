/*
 * Author: QuanPH
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */
package vn.com.onesoft.bigfox.server.io.core.compress;

/**
 *
 * @author QuanPH
 */
public class CompressManager {

    private ICompress cp;
    
    private static CompressManager _instance = null;

    public static CompressManager getInstance() {
        if (_instance == null) {
            _instance = new CompressManager();
            _instance.setCompress(new BFZip());
        }
        return _instance;
    }
    
    private void setCompress(ICompress compress){
        this.cp = compress;
    }
    
    public byte[] compress(byte[] data){
        return cp.compress(data);
    }
    
    public byte[] decompress(byte[] data){
        return cp.decompress(data);
    }
}
