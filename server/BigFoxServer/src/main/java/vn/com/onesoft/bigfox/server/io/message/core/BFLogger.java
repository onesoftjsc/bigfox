/*
 * Author: QuanPH
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */
package vn.com.onesoft.bigfox.server.io.message.core;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

/**
 *
 * @author QuanPH
 */
public class BFLogger {

    public static Logger logger = Logger.getLogger(BFLogger.class);

    static {
        DOMConfigurator.configure("log4j_config.xml");
        Logger.getLogger("com.mchange.v2").setLevel(Level.WARN);
    }

    private static BFLogger _instance = null;

    public static BFLogger getInstance() {
        if (_instance == null) {
            _instance = new BFLogger();
        }
        return _instance;
    }

    public void info(Object obj) {
        if (obj instanceof MessageIO &&  ((MessageIO) obj).isCore())
            return;
        logger.info(obj);
    }

    public void error(Object obj, Throwable t) {
        logger.error(obj, t);
    }

    public void error(Object obj) {
        logger.error(obj);
    }
}
