/*
 * Author: QuanPH
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */
package vn.com.onesoft.bigfox.server.helper.classmanager;

import com.google.common.collect.MapMaker;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import vn.com.onesoft.bigfox.server.io.message.base.BFLogger;
import vn.com.onesoft.bigfox.server.main.BFConfig;

/**
 *
 * @author QuanPH
 */
public class Scanner extends Thread {

    public Map<String, String> mapFileToCreatedTime = new MapMaker().makeMap();

    private static Scanner _instance = null;

    public static Scanner getInstance() {
        if (_instance == null) {
            _instance = new Scanner();
            _instance.start();
        }
        return _instance;
    }

    @Override
    public void run() {
        String[] paths = BFConfig.getInstance().getReloadClassPaths();
        if (paths == null) {
            return;
        }

        while (true) {

            try {
                for (int i = 0; i < paths.length; i++) {
                    String path = paths[i];
                    File file = new File(path);
                    File[] listOfFiles = file.listFiles();
                    if (listOfFiles != null) {
                        for (int j = 0; j < listOfFiles.length; j++) {
                            if (listOfFiles[j].isFile()) {
                                File classFile = listOfFiles[j];
                                if (classFile.getAbsolutePath().contains(".class")) {
                                    String oldCS = mapFileToCreatedTime.get(classFile.getAbsolutePath());
                                    String checksum = checksum(classFile);
                                    if (oldCS == null) {
                                        onNewFile(classFile.getAbsolutePath());
                                    } else if (oldCS.compareTo(checksum) != 0) {
                                        onChangedFile(classFile.getAbsolutePath());
                                    }
                                    mapFileToCreatedTime.put(classFile.getAbsolutePath(), checksum);
                                }
                            }
                        }
                    }
                }

                Thread.sleep(1000);
            } catch (Exception ex) {
                BFLogger.getInstance().error(ex.getMessage(), ex);
            }
        }
    }

    public void onNewFile(String path) {
        ClassReloader.reloadClass(path);
    }

    public void onChangedFile(String path) {
        ClassReloader.reloadClass(path);
    }

    private String checksum(File file) throws NoSuchAlgorithmException, FileNotFoundException, IOException {
        MessageDigest md = MessageDigest.getInstance("SHA1");
        FileInputStream fis = new FileInputStream(file);
        byte[] dataBytes = new byte[1024];

        int nread = 0;

        while ((nread = fis.read(dataBytes)) != -1) {
            md.update(dataBytes, 0, nread);
        };

        byte[] mdbytes = md.digest();

        //convert the byte to hex format
        StringBuffer sb = new StringBuffer("");
        for (int i = 0; i < mdbytes.length; i++) {
            sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }

}
