package vn.com.onesoft.bigfox.server.io.message.core.sc;

/*
 * Author: QuanPH
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */



import vn.com.onesoft.bigfox.server.io.message.core.MessageOut;
import vn.com.onesoft.bigfox.server.io.message.core.Tags;
import vn.com.onesoft.bigfox.server.io.message.core.annotations.Message;
import vn.com.onesoft.bigfox.server.io.message.core.annotations.Property;

/**
 *
 * @author QuanPH
 */
@Message(tag = Tags.SC_INIT_SESSION, name = "SC_INIT_SESSION", isCore = true)
public class SCInitSession extends MessageOut {

    @Property(name = "sessionStatus")
    public int sessionStatus;

    public static final int START_NEW_SESSION = 0x01;
    public static final int CONTINUE_OLD_SESSION = 0x02;
    
    public SCInitSession(int sessionStatus) {
        this.sessionStatus = sessionStatus;
    }
    
    
    
}
