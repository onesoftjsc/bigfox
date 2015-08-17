/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.com.onesoft.bigfox.server.io.message.base;

import vn.com.onesoft.bigfox.server.io.message.annotations.Message;

/**
 *
 * @author Quan
 */
public abstract class MessageIO {

    protected int length;
    protected int tag;
    protected int mSequence;
    protected int sSequence;
    protected int status;
    private int checkSum;

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " " + BigFoxUtils.toString(this);
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
     * @return the sequence
     */
    public int getMSequence() {
        return mSequence;
    }

    /**
     * @param sequence the sequence to set
     */
    public void setMSequence(int sequence) {
        this.mSequence = sequence;
    }

    /**
     * @return the sSequence
     */
    public int getSSequence() {
        return sSequence;
    }

    /**
     * @param sSequence the sSequence to set
     */
    public void setSSequence(int sSequence) {
        this.sSequence = sSequence;
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

    public boolean isCore() {
        try {
            Message m = this.getClass().getAnnotation(Message.class);
            return m.isCore();
        } catch (Exception ex) {
            return false;
        }
    }
}
