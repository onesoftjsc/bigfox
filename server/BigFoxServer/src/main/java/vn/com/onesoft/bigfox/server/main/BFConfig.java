/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.com.onesoft.bigfox.server.main;

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

    private int portSocket;
    private int portWebSocket;
    private boolean enableZip;

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
            File file = new File("server.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();

            NodeList SPortSocket = doc.getElementsByTagName("port_socket");
            Element ePortSocket = (Element) SPortSocket.item(0);
            NodeList nPortSocket = ePortSocket.getChildNodes();
            this.portSocket = Integer.parseInt(((Node) nPortSocket.item(0)).getNodeValue());

            NodeList SPortWebSocket = doc.getElementsByTagName("port_websocket");
            Element ePortWebSocket = (Element) SPortWebSocket.item(0);
            NodeList nPortWebSocket = ePortWebSocket.getChildNodes();
            this.portWebSocket = Integer.parseInt(((Node) nPortWebSocket.item(0)).getNodeValue());

            NodeList SZip = doc.getElementsByTagName("enable_zip");
            Element eZip = (Element) SZip.item(0);
            NodeList nZip = eZip.getChildNodes();
            this.enableZip = (Integer.parseInt(((Node) nZip.item(0)).getNodeValue()) == 1);
        } catch (Exception ex) {

        }
    }

    /**
     * @return the portSocket
     */
    public int getPortSocket() {
        return portSocket;
    }

    /**
     * @return the portWebSocket
     */
    public int getPortWebSocket() {
        return portWebSocket;
    }
    
    public boolean enableZip(){
        return enableZip;
    }

}
