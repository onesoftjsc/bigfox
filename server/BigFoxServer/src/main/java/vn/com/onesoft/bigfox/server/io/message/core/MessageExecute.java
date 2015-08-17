/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.com.onesoft.bigfox.server.io.message.core;

import com.google.common.collect.MapMaker;
import io.netty.channel.Channel;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.reflections.Reflections;
import vn.com.onesoft.bigfox.server.io.message.core.annotations.Message;
import vn.com.onesoft.bigfox.server.io.core.session.BFSessionManager;
import vn.com.onesoft.bigfox.server.main.Main;

/**
 *
 * @author Quan
 */
public class MessageExecute {

    private static MessageExecute messageExecute = null;
    public static Map<Integer, Map<Integer, MessageIn>> mapAppIdToMapTagToMessage = new MapMaker().concurrencyLevel(4).makeMap();
    public static Map<Integer, MessageIn> mapTagToMessage = new MapMaker().concurrencyLevel(4).makeMap();
    
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
        Reflections reflectionMI = new Reflections("vn.com.onesoft.bigfox.server.io.message");
        Set<Class<? extends MessageIn>> subMessageIns = reflectionMI.getSubTypesOf(MessageIn.class);
        Iterator itMI = subMessageIns.iterator();
        while (itMI.hasNext()) {
            try {
                Class<?> mClassMI = (Class) itMI.next();
                Message mAnnotation = mClassMI.getAnnotation(Message.class);
                mapTagToMessage.put(mAnnotation.tag(), (MessageIn) mClassMI.newInstance());
            } catch (Exception exx) {
                BFLogger.getInstance().error(exx.getMessage(), exx);
            }
        }
    }

    public void onMessage(Channel channel, byte[] data) throws Exception {
        int tag = (int) (((data[4] & 0xff) << 24) | ((data[5] & 0xff) << 16) | ((data[6] & 0xff) << 8) | ((data[7] & 0xff)));
        boolean valid = isValid(channel, data);
        if (valid) {
            MessageIn rootMMess = mapTagToMessage.get(tag);
            if (rootMMess != null) {

                DataInputStream in = new DataInputStream(
                        new ByteArrayInputStream(data));
                int len = in.readInt();// length
                 in.readInt();// tag
                int mSequence = in.readInt();
                int sSequence = in.readInt();
                int status = in.readInt();
                int checkSum = in.readInt();
                MessageIn message = (MessageIn) BigFoxUtils.fromBytes(rootMMess.getClass(), in);
                message.setLength(len);
                message.setTag(tag);
                message.setMSequence(mSequence);
                message.setSSequence(sSequence);
                message.setStatus(status);
                message.setCheckSum(checkSum);
                BFSessionManager.getInstance().onMessage(channel, message);
            }
        }
    }

    private boolean isValid(Channel channel, byte[] data) {
        return true;
    }

}
