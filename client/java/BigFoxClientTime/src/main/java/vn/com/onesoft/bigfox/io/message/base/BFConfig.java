/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.com.onesoft.bigfox.io.message.base;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;

/**
 *
 * @author phamquan
 */
public class BFConfig {

    private String ip;
    private int port;
    private int version;
    private String zone;
    
    private static BFConfig _instance = null;

    public static BFConfig getInstance() {
        if (_instance == null) {
            _instance = new BFConfig();
            _instance.loadConfig();
        }
        return _instance;
    }

    private void loadConfig() {
        try {
            File file = new File("config.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();

            this.ip = doc.getElementsByTagName("ip").item(0).getChildNodes().item(0).getNodeValue();
            this.port = Integer.parseInt(doc.getElementsByTagName("port").item(0).getChildNodes().item(0).getNodeValue());
            this.version = Integer.parseInt(doc.getElementsByTagName("version").item(0).getChildNodes().item(0).getNodeValue());
            this.zone = doc.getElementsByTagName("zone").item(0).getChildNodes().item(0).getNodeValue();

        } catch (Exception ex) {

        }
    }

    /**
     * @return the ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * @return the port
     */
    public int getPort() {
        return port;
    }


    /**
     * @return the version
     */
    public int getVersion() {
        return version;
    }

    /**
     * @return the zone
     */
    public String getZone() {
        return zone;
    }


}
