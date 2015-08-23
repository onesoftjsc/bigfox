package vn.com.onesoft.bigfox.io.message.core.sc;

/*
 * Author: QuanPH
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */



import io.netty.channel.Channel;
import vn.com.onesoft.bigfox.io.message.annotations.Message;
import vn.com.onesoft.bigfox.io.message.annotations.Property;
import vn.com.onesoft.bigfox.io.message.base.MessageIn;
import vn.com.onesoft.bigfox.io.message.core.tags.CoreTags;

/**
 *
 * @author QuanPH
 */
@Message(tag = CoreTags.SC_INIT_SESSION, name = "SC_INIT_SESSION", isCore = true)
public class SCInitSession extends MessageIn{

    @Property(name = "sessionStatus")
    public int sessionStatus;

    public static final int START_NEW_SESSION = 0x01;
    public static final int CONTINUE_OLD_SESSION = 0x02;
    
    public SCInitSession(int sessionStatus) {
        this.sessionStatus = sessionStatus;
    }

    @Override
    public void execute(Channel channel) {
        
    }
    
    
    
}
