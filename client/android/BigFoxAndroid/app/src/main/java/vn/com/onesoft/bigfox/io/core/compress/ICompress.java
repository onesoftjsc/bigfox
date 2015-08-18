package vn.com.onesoft.bigfox.io.core.compress;

/**
 * Created by phamquan on 8/18/15.
 */
public interface ICompress {
    public byte[] compress(byte[] data);
    public byte[] decompress(byte[] data);
}
