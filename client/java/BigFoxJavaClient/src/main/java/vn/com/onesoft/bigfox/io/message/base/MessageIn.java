/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.com.onesoft.bigfox.io.message.base;

import io.netty.channel.Channel;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;



/**
 *
 * @author Quan
 */
public abstract class MessageIn extends BaseMessage {

    private static short TAG = 0;
    ByteArrayInputStream byteArrayInput;
    byte[] data = new byte[0]; //phucpq add
    DataInputStream in;
    public final static int _HEADER_LENGTH = 17;

    public MessageIn() {
    }

    public MessageIn(byte[] data, Channel channel) {
        if (data == null) {
            return;
        }
        this.data = data; //phucpq add
        byteArrayInput = new ByteArrayInputStream(data);
        in = new DataInputStream(byteArrayInput);
    }

    public abstract void execute(Channel channel);
}
