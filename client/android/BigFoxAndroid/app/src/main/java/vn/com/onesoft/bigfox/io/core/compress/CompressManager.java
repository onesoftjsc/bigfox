package vn.com.onesoft.bigfox.io.core.compress;

/**
 * Created by phamquan on 8/18/15.
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
        if(true)
            return data;
        return cp.compress(data);
    }

    public byte[] decompress(byte[] data){
        return cp.decompress(data);
    }
}

