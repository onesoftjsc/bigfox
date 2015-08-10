/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.com.onesoft.bigfox.server.io.message.core;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import vn.com.onesoft.bigfox.server.io.message.core.annotations.Message;

/**
 *
 * @author Quan
 */
public abstract class MessageOut extends MessageIO {

    private ByteArrayOutputStream byteArrayOutput;
    private DataOutputStream out;

    public MessageOut() {
        byteArrayOutput = new ByteArrayOutputStream();
        out = new DataOutputStream(byteArrayOutput);
    }

    public byte[] toBytes() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(baos);
            Message m = getClass().getAnnotation(Message.class);
            if (m != null) {
                tag = m.tag();
            }
            out.writeInt(0);
            out.writeInt(tag);
            out.writeInt(mSequence);
            out.writeInt(sSequence);
            out.writeInt(status);
            out.writeInt(getCheckSum());
            BigFoxUtils.write(this, out);
            byte[] data = baos.toByteArray();
            length = data.length; // 4 byte length, 4 byte tag
            data[0] = (byte) ((length >> 24) & 0x00ff);
            data[1] = (byte) ((length >> 16) & 0x00ff);
            data[2] = (byte) ((length >> 8) & 0x00ff);
            data[3] = (byte) ((length) & 0x00ff);
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
