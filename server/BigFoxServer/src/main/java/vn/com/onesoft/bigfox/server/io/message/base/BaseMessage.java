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
public abstract class BaseMessage {

    protected int length;
    protected int tag;
    protected int mSequence;
    protected int sSequence;
    protected int status;
    private int checkSum;

    public final static int STATUS_CORE = 0x01;
    public final static int STATUS_ZIP = 0x02;
    public final static int STATUS_CONTINUE = 0x04;

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " m:" + this.getMSequence() + " s:" + this.getSSequence() + " " + BigFoxUtils.toString(this);
    }

    /**
     * @return the length
     */
    public int getLength() {
        return length;
    }

    /**
     * HuongNS update
     * @param length the length to set
     */
    public final void setLength(int length) {
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
        if (this.isCore()) {
            status = status | BaseMessage.STATUS_CORE;
        }
        return status;
    }

    /**
     * HuongNS update
     * @param status the status to set
     */
    public final void setStatus(int status) {
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
