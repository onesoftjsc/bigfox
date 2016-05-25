/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.com.onesoft.bigfox.server.main;

import vn.com.onesoft.bigfox.server.io.message.base.BFLogger;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;

/**
 *
 * @author phamquan
 */
public class BFConfig {

    private int portSocket;
    private int portWebSocket;
    private int portTelnet;
    private int sessionTimeout;
    private String certificateFile;
    private String privateFile;

    private boolean enableZip;
    private String[] reloadClassPaths = null;

    private static BFConfig _instance = null;

    public static String APPLICATION_FOLDER = "applications";
    public static String AUTODEPLOY_FOLDER = "autodeploys";

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

            this.portSocket = Integer.parseInt(doc.getElementsByTagName("port_socket").item(0).getChildNodes().item(0).getNodeValue());
            this.portWebSocket = Integer.parseInt(doc.getElementsByTagName("port_websocket").item(0).getChildNodes().item(0).getNodeValue());
            this.portTelnet = Integer.parseInt(doc.getElementsByTagName("port_telnet").item(0).getChildNodes().item(0).getNodeValue());
            this.enableZip = (Integer.parseInt(doc.getElementsByTagName("enable_zip").item(0).getChildNodes().item(0).getNodeValue()) == 1);
            this.reloadClassPaths = doc.getElementsByTagName("reload_class_path").item(0).getChildNodes().item(0).getNodeValue().split(";");
            this.sessionTimeout = Integer.parseInt(doc.getElementsByTagName("session_timeout").item(0).getChildNodes().item(0).getNodeValue());
            this.certificateFile = doc.getElementsByTagName("certificate_file").item(0).getChildNodes().item(0).getNodeValue();
            this.privateFile = doc.getElementsByTagName("private_file").item(0).getChildNodes().item(0).getNodeValue();
        } catch (Exception ex) {
            BFLogger.getInstance().error(ex.getMessage(), ex);
            System.exit(0);
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

    public int getPortTelnet() {
        return portTelnet;
    }

    public boolean enableZip() {
        return enableZip;
    }

    /**
     * @return the reloadClassPaths
     */
    public String[] getReloadClassPaths() {
        return reloadClassPaths;
    }

    public int getSessionTimeout() {
        return sessionTimeout;
    }

    /**
     * @return the certificateFile
     */
    public String getCertificateFile() {
        return certificateFile;
    }

    /**
     * @return the privateFile
     */
    public String getPrivateFile() {
        return privateFile;
    }

}
