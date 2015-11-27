/*
 * Author: QuanPH
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */
package vn.com.onesoft.bigfox.server.helper.classmanager;

import com.google.common.collect.MapMaker;
import java.util.Map;
import vn.com.onesoft.bigfox.server.io.core.business.zone.BFZoneManager;
import vn.com.onesoft.bigfox.server.io.message.base.BFLogger;

/**
 *
 * @author QuanPH
 */
public class MonitorFileChanged extends Thread {

    public Map<String, String> mapFileToCreatedTime = new MapMaker().makeMap();

    private static MonitorFileChanged _instance = null;

    public static MonitorFileChanged getInstance() {
        if (_instance == null) {
            _instance = new MonitorFileChanged();
            _instance.start();
        }
        return _instance;
    }

    @Override
    public void run() {
        while (true) {

            try {
//                BFZoneManager.getInstance().reloadChangedZones();
                Thread.sleep(1000);
            } catch (Exception ex) {
                BFLogger.getInstance().error(ex.getMessage(), ex);
            }
        }
    }

    public void onNewFile(String path) {
//        new ClassReloader(Main.class.getClassLoader()).reloadClass(path);
    }

    public void onChangedFile(String path) {
//        new ClassReloader(Main.class.getClassLoader()).reloadClass(path);
    }



}
