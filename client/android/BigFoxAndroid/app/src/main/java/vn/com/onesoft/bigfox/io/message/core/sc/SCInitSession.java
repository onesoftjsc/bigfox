package vn.com.onesoft.bigfox.io.message.core.sc;

import vn.com.onesoft.bigfox.io.core.session.ConnectionManager;
import vn.com.onesoft.bigfox.io.core.session.PingThreadManager;
import vn.com.onesoft.bigfox.io.message.BaseMessage;
import vn.com.onesoft.bigfox.io.message.IMessageIn;
import vn.com.onesoft.bigfox.io.message.core.tags.CoreTags;
import vn.com.onesoft.bigfox.io.message.annotations.Message;
import vn.com.onesoft.bigfox.io.message.annotations.Property;

/* 
 * Author: QuanPH
 * Copyright @2015
 */
@Message(tag = CoreTags.SC_INIT_SESSION, name = "SC_INIT_SESSION", isCore = true)
public class SCInitSession extends BaseMessage implements IMessageIn {

    @Property(name = "sessionStatus")
    public int sessionStatus;

    public static final int START_NEW_SESSION = 0x01;
    public static final int CONTINUE_OLD_SESSION = 0x02;
    
    @Override
    public void execute() {
    	// TODO Auto-generated method stub
    	if (sessionStatus == START_NEW_SESSION){
    		ConnectionManager.getInstance().onStartNewSession();
    		PingThreadManager.getInstance();
    	}else{
    		ConnectionManager.getInstance().resendOldMessages();
    		ConnectionManager.getInstance().onContinueOldSession();
    	}
    }
    
    	
}
