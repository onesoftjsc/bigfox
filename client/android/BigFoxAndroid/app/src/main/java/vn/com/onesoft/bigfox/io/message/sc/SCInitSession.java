package vn.com.onesoft.bigfox.io.message.sc;

import vn.com.onesoft.bigfox.io.core.ConnectionManager;
import vn.com.onesoft.bigfox.io.core.PingThreadManager;
import vn.com.onesoft.bigfox.io.message.core.BaseMessage;
import vn.com.onesoft.bigfox.io.message.core.IMessageIn;
import vn.com.onesoft.bigfox.io.message.core.Tags;
import vn.com.onesoft.bigfox.io.message.core.annotations.Message;
import vn.com.onesoft.bigfox.io.message.core.annotations.Property;

/* 
 * Author: QuanPH
 * Copyright @2015
 */
@Message(tag = Tags.SC_INIT_SESSION, name = "SC_INIT_SESSION", isCore = true)
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
