package vn.com.onesoft.bigfox.server.io.message.core.sc;

/*
 * Author: QuanPH
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */



import vn.com.onesoft.bigfox.server.io.message.annotations.Message;
import vn.com.onesoft.bigfox.server.io.message.annotations.Property;
import vn.com.onesoft.bigfox.server.io.message.base.MessageOut;
import vn.com.onesoft.bigfox.server.io.message.core.tags.CoreTags;

/**
 *
 * @author QuanPH
 */
@Message(tag = CoreTags.SC_INIT_SESSION, name = "SC_INIT_SESSION", isCore = true)
public class SCInitSession extends MessageOut {

    @Property(name = "sessionStatus")
    public int sessionStatus;
    @Property(name="pingPeriod")
    public int pingPeriod;
    @Property(name="timeRetriesToReconnect")
    public int timeRetriesToReconnect;

    public static final int START_NEW_SESSION = 0x01;
    public static final int CONTINUE_OLD_SESSION = 0x02;
    
    public SCInitSession(int sessionStatus, int pingPeriod, int timeRetriesToReconnect) {
        this.sessionStatus = sessionStatus;
        this.pingPeriod = pingPeriod;
        this.timeRetriesToReconnect = timeRetriesToReconnect;
    }

    @Override
    public MessageOut clone() {
        return new SCInitSession(sessionStatus, pingPeriod, timeRetriesToReconnect);
    }

}
