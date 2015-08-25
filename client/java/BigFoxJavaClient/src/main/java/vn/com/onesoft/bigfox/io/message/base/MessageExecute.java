/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.com.onesoft.bigfox.io.message.base;

import com.google.common.collect.MapMaker;
import io.netty.channel.Channel;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.reflections.Reflections;
import vn.com.onesoft.bigfox.io.core.session.ConnectionManager;
import vn.com.onesoft.bigfox.io.message.annotations.Message;

/**
 *
 * @author Quan
 */
public class MessageExecute {

    private static MessageExecute messageExecute = null;
    public static Map<Integer, MessageIn> mapTagToCoreMessage = new MapMaker().concurrencyLevel(4).makeMap();
    public static Map<Integer, MessageIn> mapTagToUserMessage = new MapMaker().concurrencyLevel(4).makeMap();

    public static ArrayList<Short> baotriTags = new ArrayList<>();

    private MessageExecute() {

    }

    public static MessageExecute getInstance() {
        if (messageExecute == null) {
            messageExecute = new MessageExecute();
            messageExecute.loadCoreClasses();
        }
        return messageExecute;
    }

    public void loadCoreClasses() {
        Reflections reflectionMI = new Reflections("vn.com.onesoft.bigfox");
        Set<Class<? extends MessageIn>> subMessageIns = reflectionMI.getSubTypesOf(MessageIn.class);
        Iterator itMI = subMessageIns.iterator();
        while (itMI.hasNext()) {
            try {
                Class<?> mClassMI = (Class) itMI.next();
                Message mAnnotation = mClassMI.getAnnotation(Message.class);
                if (mAnnotation.isCore()) {
                    mapTagToCoreMessage.put(mAnnotation.tag(), (MessageIn) mClassMI.newInstance());
                } else {
                    mapTagToUserMessage.put(mAnnotation.tag(), (MessageIn) mClassMI.newInstance());
                }
            } catch (Exception exx) {
                BFLogger.getInstance().error(exx.getMessage(), exx);
            }
        }
    }

    public void onMessage(Channel channel, byte[] data) throws Exception {
        int tag = (int) (((data[4] & 0xff) << 24) | ((data[5] & 0xff) << 16) | ((data[6] & 0xff) << 8) | ((data[7] & 0xff)));
        int status = (int) (((data[16] & 0xff) << 24) | ((data[17] & 0xff) << 16) | ((data[18] & 0xff) << 8) | ((data[19] & 0xff)));
        boolean isCore = (status & 0x01) == 0x01;
        boolean valid = isValid(channel, data);
        if (valid) {

            MessageIn rootMMess = null;
            if (isCore) {
                rootMMess = mapTagToCoreMessage.get(tag);
            } else {
                rootMMess = mapTagToUserMessage.get(tag);
            }
            if (rootMMess != null) {
                DataInputStream in = new DataInputStream(
                        new ByteArrayInputStream(data));
                int len = in.readInt();// length
                in.readInt();// tag
                int mSequence = in.readInt();
                int sSequence = in.readInt();
                in.readInt();// status
                int checkSum = in.readInt();
                MessageIn message = (MessageIn) BigFoxUtils.fromBytes(rootMMess.getClass(), in);
                message.setLength(len);
                message.setTag(tag);
                message.setMSequence(mSequence);
                message.setSSequence(sSequence);
                message.setStatus(status);
                message.setCheckSum(checkSum);
                ConnectionManager.getInstance().onMessage(channel, message);
            }
        }
    }

    private boolean isValid(Channel channel, byte[] data) {
        return true;
    }

}
