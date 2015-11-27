/*
 * Author: QuanPH
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */
package vn.com.onesoft.bigfox.server.helper.classmanager;

import com.google.common.collect.MapMaker;
import java.io.File;
import java.util.Map;
import vn.com.onesoft.bigfox.server.io.core.business.zone.BFZoneManager;
import vn.com.onesoft.bigfox.server.io.message.base.BFLogger;
import vn.com.onesoft.bigfox.server.main.BFConfig;

/**
 *
 * @author QuanPH
 */
public class Extracter extends Thread {

    private static Extracter _instance = null;
    private Map<String, Class> mapNameToClass = new MapMaker().makeMap();

    public static Extracter getInstance() {
        if (_instance == null) {
            _instance = new Extracter();
            _instance.start();
        }
        return _instance;
    }

    @Override
    public void run() {

        while (true) {
            try {
                scan();
            } catch (Exception ex) {
                BFLogger.getInstance().error(ex.getMessage(), ex);
            } finally {
                try {
                    Thread.sleep(1000);
                } catch (Exception tex) {
                    
                }
            }
        }
    }

    public void extract(String jarFile) throws Exception {
        java.util.jar.JarFile jar = new java.util.jar.JarFile(jarFile);
        java.util.Enumeration enumEntries = jar.entries();

        File fileJ = new File(jarFile);
        String nameF = fileJ.getName();
        String name = nameF.substring(0, nameF.indexOf("."));
        File appRootFolder = new File(new File(".").getCanonicalPath() + "/" + BFConfig.APPLICATION_FOLDER + "/" + name);
        appRootFolder.mkdir();

        while (enumEntries.hasMoreElements()) {
            java.util.jar.JarEntry file = (java.util.jar.JarEntry) enumEntries.nextElement();
            java.io.File f = new java.io.File(new File(".").getCanonicalPath() + "/" + BFConfig.APPLICATION_FOLDER + "/" + name + "/" + java.io.File.separator + file.getName());
            if (file.isDirectory()) {
                f.mkdir();
                continue;
            }
            java.io.InputStream is = jar.getInputStream(file);
            java.io.FileOutputStream fos = new java.io.FileOutputStream(f);
            while (is.available() > 0) {
                fos.write(is.read());
            }
            fos.close();
            is.close();
        }

    }

    private void scan() throws Exception {
        String path = new File(".").getCanonicalPath() + "/" + BFConfig.AUTODEPLOY_FOLDER;
        File file = new File(path);
        File[] listFile = file.listFiles();
        for (int i = 0; i < listFile.length; i++) {
            if (listFile[i].isFile()) {
                File scanedFile = listFile[i];
                if (scanedFile.getAbsolutePath().contains(".jar")) {
                    extract(scanedFile.getAbsolutePath());
                    String nameF = scanedFile.getName();
                    String name = nameF.substring(0, nameF.indexOf("."));
                    BFZoneManager.getInstance().loadZone(new File(".").getCanonicalPath() + "/" + BFConfig.APPLICATION_FOLDER + "/" + name);
                    scanedFile.delete();
                }
            }
        }
    }
    
    
}
