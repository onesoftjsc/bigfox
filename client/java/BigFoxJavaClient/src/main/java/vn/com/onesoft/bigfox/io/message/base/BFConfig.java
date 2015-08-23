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
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author phamquan
 */
public class BFConfig {

    private String ip;
    private int port;

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

            NodeList SPortSocket = doc.getElementsByTagName("ip");
            Element ePortSocket = (Element) SPortSocket.item(0);
            NodeList nPortSocket = ePortSocket.getChildNodes();
            this.setIp(((Node) nPortSocket.item(0)).getNodeValue());

            NodeList SPortWebSocket = doc.getElementsByTagName("port");
            Element ePortWebSocket = (Element) SPortWebSocket.item(0);
            NodeList nPortWebSocket = ePortWebSocket.getChildNodes();
            this.setPort(Integer.parseInt(((Node) nPortWebSocket.item(0)).getNodeValue()));

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
     * @param ip the ip to set
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * @return the port
     */
    public int getPort() {
        return port;
    }

    /**
     * @param port the port to set
     */
    public void setPort(int port) {
        this.port = port;
    }


}
