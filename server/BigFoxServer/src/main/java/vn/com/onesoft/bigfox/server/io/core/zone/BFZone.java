/*
 * Author: QuanPH
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */
package vn.com.onesoft.bigfox.server.io.core.zone;

import com.google.common.collect.MapMaker;
import io.netty.channel.Channel;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import vn.com.onesoft.bigfox.server.helper.utils.BFUtils;
import vn.com.onesoft.bigfox.server.io.core.session.BFSessionManager;
import vn.com.onesoft.bigfox.server.io.core.session.IBFSession;
import vn.com.onesoft.bigfox.server.io.message.annotations.Message;
import vn.com.onesoft.bigfox.server.io.message.base.MessageIn;
import vn.com.onesoft.bigfox.server.io.message.base.MessageOut;

/**
 *
 * @author QuanPH
 */
public class BFZone implements IBFZone {

    private Map<Channel, IBFSession> mapChannelToSession = new MapMaker().makeMap();
    protected Map<Integer, MessageIn> mapTagToUserMessage = new MapMaker().concurrencyLevel(4).makeMap();
    private BFClassLoader zoneCL;
    private String absolutePath;
    private String simpleName;
    private Map<String, String> mapFileNameToChecksum = new MapMaker().makeMap();

    private BFZone() {

    }

    public BFZone(String absolutePath) {
        this.absolutePath = absolutePath;
        int k = this.absolutePath.length() - 1;
        for (int i = this.absolutePath.length() - 1; i > 0; i--) {
            if (this.absolutePath.charAt(i) == '/' || this.absolutePath.charAt(i) == '\\') {
                k = i;
                break;
            }
        }
        this.simpleName = absolutePath.substring(k + 1);
        zoneCL = new BFClassLoader(BFClassLoader.class.getClassLoader());
    }

    public void addSession(IBFSession session) {
        mapChannelToSession.put(session.getChannel(), session);
    }

    public void restartZone() {

    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void restart() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onMessage(Channel channel, byte[] data) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BFClassLoader getClassLoader() {
        return zoneCL;
    }

    @Override
    public IBFSession getSession(String sessionId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IBFSession getSession(Channel channel) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void loadZone() throws Exception {
        loadFolder(absolutePath);
        Iterator it = zoneCL.getMapPathToClass().values().iterator();
        while (it.hasNext()) {
            Class cls = (Class) it.next();
            if (MessageIn.class.isAssignableFrom(cls)) {
                MessageIn mess = (MessageIn) cls.newInstance();
                Message m = (Message) mess.getClass().getAnnotation(Message.class);
                mapTagToUserMessage.put(m.tag(), mess);
            }
        }
    }

    @Override
    public String getSimpleName() {
        return simpleName;
    }

    @Override
    public MessageIn getMessage(int tag) {
        return mapTagToUserMessage.get(tag);
    }

    @Override
    public void sendMessageToAll(MessageOut mOut) {
        Iterator it = mapChannelToSession.keySet().iterator();
        while (it.hasNext()) {
            Channel channel = (Channel) it.next();
            BFSessionManager.getInstance().sendMessage(channel, mOut.clone());
        }
    }

    private ArrayList<File> listFileChanged(String folderName) throws IOException, FileNotFoundException, NoSuchAlgorithmException {
        ArrayList<File> result = new ArrayList<File>();
        File folder = new File(folderName);
        File[] files = folder.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                result.addAll(listFileChanged(file.getAbsolutePath()));
            } else if (file.isFile() && file.getAbsolutePath().contains(".class")) {
                if (mapFileNameToChecksum.get(file.getAbsolutePath()) != null && mapFileNameToChecksum.get(file.getAbsolutePath()).compareTo(BFUtils.checksum(file)) != 0) {
                    result.add(file);
                }
            }

        }
        return result;
    }

    private ArrayList<File> listFileAdded(String folderName) throws IOException, FileNotFoundException, NoSuchAlgorithmException {
        ArrayList<File> result = new ArrayList<File>();
        File folder = new File(folderName);
        File[] files = folder.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                result.addAll(listFileAdded(file.getAbsolutePath()));
            } else if (file.isFile() && file.getAbsolutePath().contains(".class")) {
                if (mapFileNameToChecksum.get(file.getAbsolutePath()) == null) {
                    result.add(file);
                }
            }
        }
        return result;
    }

    @Override
    public void reloadFilesChanged() throws Exception {
        BFClassLoader cl = new BFClassLoader(zoneCL);
        ArrayList<File> changedFiles = listFileChanged(absolutePath);
        ArrayList<File> addedFiles = listFileAdded(absolutePath);
        for (File changedFile : changedFiles) {
            loadFile(changedFile.getAbsolutePath(), cl);
        }

        for (File addedFile : addedFiles) {
            loadFile(addedFile.getAbsolutePath(), zoneCL);
        }

        Iterator it = cl.getMapPathToClass().values().iterator();
        while (it.hasNext()) {
            Class cls = (Class) it.next();
            if (MessageIn.class.isAssignableFrom(cls)) {
                MessageIn mess = (MessageIn) cls.newInstance();
                Message m = (Message) mess.getClass().getAnnotation(Message.class);
                mapTagToUserMessage.put(m.tag(), mess);
            }
        }
    }

    private void loadFile(String filePath, BFClassLoader cl) throws IOException, FileNotFoundException, NoSuchAlgorithmException {
        if (filePath.contains("CS") || filePath.contains("SC")) {
            cl.loadFile(filePath);
            mapFileNameToChecksum.put(filePath, BFUtils.checksum(new File(filePath)));
        }

    }

    private void loadFolder(String foldPath) throws IOException, FileNotFoundException, NoSuchAlgorithmException {
        File folder = new File(foldPath);
        File[] files = folder.listFiles();
        for (File file : files) {
            if (file.isFile() && file.getAbsolutePath().contains(".class")) {

                loadFile(file.getAbsolutePath(), zoneCL);
            } else if (file.isDirectory()) {
                loadFolder(file.getAbsolutePath());
            }
        }
    }

}
