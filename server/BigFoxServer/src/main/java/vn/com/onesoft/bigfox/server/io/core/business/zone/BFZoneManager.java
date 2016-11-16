/*
 * Author: QuanPH
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */
package vn.com.onesoft.bigfox.server.io.core.business.zone;

import com.google.common.collect.MapMaker;
import io.netty.channel.Channel;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import vn.com.onesoft.bigfox.server.io.message.base.BFLogger;
import vn.com.onesoft.bigfox.server.main.BFConfig;

/**
 *
 * @author QuanPH
 */
public class BFZoneManager {

    private static BFZoneManager _instance = null;

    public static BFZoneManager getInstance() {
        if (_instance == null) {
            _instance = new BFZoneManager();
            try {
                _instance.loadZones();
            } catch (Exception ex) {
                BFLogger.getInstance().error(ex.getMessage(), ex);
            }
        }
        return _instance;
    }

    private Map<String, IBFZone> mapNameToZone = new MapMaker().makeMap();
    private Map<Channel, IBFZone> mapChannelToZone = new MapMaker().makeMap();
    public Map<String, IBFZone> mapTelnetPathToZone = new MapMaker().makeMap();

    public void addZone(String name, BFZone zone) {
        mapNameToZone.put(name, zone);

    }

    public void removeZone(String name) {
        mapNameToZone.remove(name);
    }

    private void loadZones() throws IOException {
        String scanPath = new File(".").getCanonicalPath() + "/" + BFConfig.APPLICATION_FOLDER;
        File file = new File(scanPath);
        File[] zoneFolders = file.listFiles();
        if (zoneFolders == null) {
            return;
        }
        for (int i = 0; i < zoneFolders.length; i++) {
            File zoneFolder = zoneFolders[i];

            try {
                if (!zoneFolder.getAbsolutePath().contains(".DS")) {
                    loadZone(zoneFolder.getAbsolutePath());
                }
            } catch (Exception ex) {
                BFLogger.getInstance().error(ex.getMessage(), ex);
            }

        }
    }

    public IBFZone loadZone(String absolutePath) {
        IBFZone zone = new BFZone(absolutePath);
        try {
            BFLogger.getInstance().info(zone.getSimpleName() + " loading ... ");
            zone.start();
            mapNameToZone.put(zone.getSimpleName(), zone);
            BFLogger.getInstance().info(zone.getSimpleName() + " loaded ... ");
            return zone;
        } catch (Exception ex) {
            BFLogger.getInstance().error(ex.getMessage(), ex);
            return null;
        }
    }

    public void reloadChangedZones() throws Exception {
        Iterator it = mapNameToZone.values().iterator();
        while (it.hasNext()) {
            IBFZone zone = (IBFZone) it.next();
            zone.reloadFilesChanged();
        }
    }

    public void reloadChangedZone(String absolutePath) throws Exception {
        IBFZone zone = mapNameToZone.get(absolutePath);
        zone.reloadFilesChanged();
    }

    public IBFZone getZone(String zoneName) {
        return mapNameToZone.get(zoneName);
    }

    public void assignChannelToZone(Channel channel, IBFZone zone) {
        mapChannelToZone.put(channel, zone);

    }

    public IBFZone getZone(Channel channel) {
        return mapChannelToZone.get(channel);
    }
}
