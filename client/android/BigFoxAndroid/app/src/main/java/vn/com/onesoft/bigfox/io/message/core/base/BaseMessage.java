package vn.com.onesoft.bigfox.io.message.core.base;

import vn.com.onesoft.bigfox.io.core.BigFox;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import vn.com.onesoft.bigfox.io.message.core.annotations.Message;

public class BaseMessage {

    private int length;
    private int tag;
    private int mSequence;
    private int sSequence;
    private int status;
    private int checkSum;

    public byte[] toBytes() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(baos);
            Message m = getClass().getAnnotation(Message.class);
            if (m != null) {
                setTag(m.tag());
            }
            out.writeInt(0);
            out.writeInt(getTag());
            out.writeInt(getmSequence());
            out.writeInt(getsSequence());
            out.writeInt(getStatus());
            out.writeInt(getCheckSum());
            BigFox.write(this, out);
            byte[] data = baos.toByteArray();
            setLength(data.length); // 4 byte length, 4 byte tag
            data[0] = (byte) ((getLength() >> 24) & 0x00ff);
            data[1] = (byte) ((getLength() >> 16) & 0x00ff);
            data[2] = (byte) ((getLength() >> 8) & 0x00ff);
            data[3] = (byte) ((getLength()) & 0x00ff);
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @return the length
     */
    public int getLength() {
        return length;
    }

    /**
     * @param length the length to set
     */
    public void setLength(int length) {
        this.length = length;
    }

    /**
     * @return the tag
     */
    public int getTag() {
        return tag;
    }

    /**
     * @param tag the tag to set
     */
    public void setTag(int tag) {
        this.tag = tag;
    }

    /**
     * @return the mSequence
     */
    public int getmSequence() {
        return mSequence;
    }

    /**
     * @param mSequence the mSequence to set
     */
    public void setmSequence(int mSequence) {
        this.mSequence = mSequence;
    }

    /**
     * @return the sSequence
     */
    public int getsSequence() {
        return sSequence;
    }

    /**
     * @param sSequence the sSequence to set
     */
    public void setsSequence(int sSequence) {
        this.sSequence = sSequence;
    }

    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * @return the checkSum
     */
    public int getCheckSum() {
        return checkSum;
    }

    /**
     * @param checkSum the checkSum to set
     */
    public void setCheckSum(int checkSum) {
        this.checkSum = checkSum;
    }

    public boolean isCore(){
        try {
            Message m = this.getClass().getAnnotation(Message.class);
            return m.isCore();
        } catch (Exception ex) {
            return false;
        }
    }

    public String toString(){
        return BigFox.toString(this);
    }

    public void execute(){
        
    }

}
