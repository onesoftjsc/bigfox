/*
 * Author: QuanPH
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */
package vn.com.onesoft.bigfox.server.io.core.business.zone;

import com.google.common.collect.MapMaker;
import io.netty.channel.Channel;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import vn.com.onesoft.bigfox.server.helper.utils.BFUtils;
import vn.com.onesoft.bigfox.server.helper.exception.NotFoundActivityException;
import vn.com.onesoft.bigfox.server.io.core.business.session.BFSessionManager;
import vn.com.onesoft.bigfox.server.io.core.business.session.IBFSession;
import vn.com.onesoft.bigfox.server.io.message.annotations.Message;
import vn.com.onesoft.bigfox.server.io.message.base.BFLogger;
import vn.com.onesoft.bigfox.server.io.message.base.MessageIn;
import vn.com.onesoft.bigfox.server.io.message.base.MessageOut;
import vn.com.onesoft.bigfox.server.main.BFConfig;
import vn.com.onesoft.bigfox.server.main.Main;

/**
 *
 * @author QuanPH
 */
public class BFZone implements IBFZone {

    private Map<Channel, IBFSession> mapChannelToSession = new MapMaker().makeMap();
    protected Map<Integer, MessageIn> mapTagToUserMessage = new MapMaker().concurrencyLevel(4).makeMap();
    private Map<String, Class> mapTelnetNameToClass = new MapMaker().concurrencyLevel(4).makeMap();

    private BFClassLoaderZone zoneCL;
    private String absolutePath;
    private String simpleName;
    private Map<String, String> mapFileNameToChecksum = new MapMaker().makeMap();
    private BFZoneActivity activity;
    private String monitorFolder = "";
    private int sessionTimeout = BFConfig.getInstance().getSessionTimeout();//s
    private int pingPeriod = 5;//s
    private int timeRetriesToReconnect = 4;// 4*5 = 20s will reconnect

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
        zoneCL = new BFClassLoaderZone(BFClassLoaderZone.class.getClassLoader(), this);
    }

    @Override
    public void setSessionTimeout(int sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
    }

    @Override
    public int getSessionTimeout() {
        return sessionTimeout;
    }

    @Override
    public void addSession(IBFSession session) {
        mapChannelToSession.put(session.getChannel(), session);
        session.setZone(this);
    }

    @Override
    public void start() throws Exception {
        loadZone();
        if (!Main.isDebug) {
            getActivity().afterZoneStart();
        }
    }

    @Override
    public void stop() {
        if (!Main.isDebug) {
            getActivity().beforeZoneStop();
        }
        BFZoneManager.getInstance().removeZone(this.getSimpleName());
        if (!Main.isDebug) {
            getActivity().afterZoneStart();
        }
    }

    @Override
    public void restart() throws Exception {
        start();
        stop();
    }

    @Override
    public BFClassLoaderZone getClassLoader() {
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

    private void loadZone() throws Exception {
        loadFolder(absolutePath);
        loadJar(absolutePath + "/" + getSimpleName() + ".jar");

        //Find Activity
        Iterator itA = zoneCL.getMapPathToClass().values().iterator();
        while (itA.hasNext()) {
            Class cls = (Class) itA.next();
            if (BFZoneActivity.class.isAssignableFrom(cls)) {
                activity = (BFZoneActivity) cls.getDeclaredConstructor(IBFZone.class).newInstance(this);
                activity.beforeZoneStart();
            }
        }
        if (activity == null && !Main.isDebug) {
            throw new NotFoundActivityException("Can not found an Activity class on jar file");
        }

        //Find CS classes
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
        ArrayList<File> result = new ArrayList<>();
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
        ArrayList<File> result = new ArrayList<>();
        File folder = new File(folderName);
        File[] files = folder.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                result.addAll(listFileAdded(file.getAbsolutePath()));
            } else if (file.isFile() && file.getAbsolutePath().contains(".class")) {
                if (mapFileNameToChecksum.get(file.getAbsolutePath()) == null
                        && (file.getAbsolutePath().contains("CS") || file.getAbsolutePath().contains("SC") || file.getAbsolutePath().contains("CMD"))) {
                    result.add(file);
                }
            }
        }
        return result;
    }

    @Override
    public void reloadFilesChanged() throws Exception {
        BFClassLoaderZone cl = new BFClassLoaderZone(zoneCL, this);
        ArrayList<File> changedFiles = listFileChanged(monitorFolder);
        ArrayList<File> addedFiles = listFileAdded(monitorFolder);
        for (File changedFile : changedFiles) {
            BFLogger.getInstance().info("File changed: " + changedFile.getName());
            loadFile(changedFile.getAbsolutePath(), cl);
        }

        for (File addedFile : addedFiles) {
            BFLogger.getInstance().info("File added: " + addedFile.getName());
            loadFile(addedFile.getAbsolutePath(), cl);
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

    private void loadFile(String filePath, BFClassLoaderZone cl) throws IOException, FileNotFoundException, NoSuchAlgorithmException {
        if (Main.isDebug) {
            if (filePath.contains("CS") || filePath.contains("SC") || filePath.contains("CMD")) {
                cl.loadFile(filePath);
                mapFileNameToChecksum.put(filePath, BFUtils.checksum(new File(filePath)));
            }
        } else {
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

    /**
     * @return the activity
     */
    public BFZoneActivity getActivity() {
        return activity;
    }

    /**
     * @param activity the activity to set
     */
    private void setActivity(BFZoneActivity activity) {
        this.activity = activity;
    }

    private void loadFolderLib(String absolutePath) {

    }

    private void loadLib() {
        String libPath = absolutePath + File.separatorChar + "lib";
        File file = new File(libPath);
        if (file.exists()) {
            File[] files = file.listFiles();
            for (File fileLib : files) {
                BFLogger.getInstance().info("Load lib: " + fileLib.getAbsolutePath());
                loadJar(fileLib.getAbsolutePath());
            }
        }
    }

    private void loadJar(String absolutePath) {

        try {
            zoneCL.loadJar(absolutePath);
        } catch (IOException ex) {
            BFLogger.getInstance().error(ex.getMessage(), ex);
        }
    }

    public Class getTelnetClass(String path) {
        return mapTelnetNameToClass.get(path);
    }

    @Override
    public String getAbsolutePath() {
        return absolutePath;
    }

    @Override
    public void setMonitorFolder(String folderPath) {
        this.monitorFolder = folderPath;
    }

    @Override
    public void setPingPeriod(int pingPeriod) {
        this.pingPeriod = pingPeriod;
    }

    @Override
    public void setReconnectRetries(int timeRetriesToReconnect) {
        this.timeRetriesToReconnect = timeRetriesToReconnect;
    }

    @Override
    public int getPingPeriod() {
        return pingPeriod;
    }

    @Override
    public int getReconnectRetries() {
        return timeRetriesToReconnect;
    }

    public Map<String, Class> getMapTelnetNameToClass() {
        return mapTelnetNameToClass;
    }
}
