/*
 * Author: QuanPH
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */
package vn.com.onesoft.bigfox.io.message.core.base;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vn.com.onesoft.bigfox.io.core.BFLogger;
import vn.com.onesoft.bigfox.io.core.BigFox;
import vn.com.onesoft.bigfox.io.core.ClassFinder;
import vn.com.onesoft.bigfox.io.core.ConnectionManager;
import vn.com.onesoft.bigfox.io.message.core.annotations.Message;

/**
 *
 * @author QuanPH
 */
public class MessageExecutor {

    
    private static MessageExecutor _instance = null;
    
    private Map<Integer, Class<?>> mapTagToClassName = new HashMap<Integer, Class<?>>();
    
    public static MessageExecutor getInstance() {
        if (_instance == null) {
            _instance = new MessageExecutor();
            _instance.loadClasses();
        }
        return _instance;
    }
    
    public void execute(byte[] data) {
        DataInputStream dis = new DataInputStream(
                new ByteArrayInputStream(data));
        int tag = 0;
        int length = 0;
        try {
            length = dis.readInt();// length
            tag = dis.readInt();
        } catch (Exception e) {
        }
        Class<?> clazz = mapTagToClassName.get(tag);
        try {
            int mSequence = dis.readInt();
            int sSequence = dis.readInt();
            int status = dis.readInt();
            int checkSum = dis.readInt();
            BaseMessage message = (BaseMessage) BigFox.fromBytes(clazz, dis);
            message.setmSequence(mSequence);
            message.setsSequence(sSequence);
            message.setStatus(status);
            message.setCheckSum(checkSum);
            dis.close();
            message.setTag(tag);
            message.setLength(length);
            Message m = clazz.getAnnotation(Message.class);
            BFLogger.getInstance().debug(m);
            ConnectionManager.getInstance().onMessage(message);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void loadClasses() {
        try {
            List<Class<?>> classes = ClassFinder.find(BaseMessage.class);
            for (Class<?> clazz : classes) {
                Message m = clazz.getAnnotation(Message.class);
                if (m.name().toUpperCase().indexOf("SC") == 0) {
                    mapTagToClassName.put(m.tag(), clazz);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
