/*
 * Author: QuanPH
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */
package vn.com.onesoft.bigfox.io.message.core.cs;

import vn.com.onesoft.bigfox.io.core.BFUtils;
import vn.com.onesoft.bigfox.io.core.ConnectionManager;
import vn.com.onesoft.bigfox.io.core.BigFoxContext;
import vn.com.onesoft.bigfox.io.message.BaseMessage;
import vn.com.onesoft.bigfox.io.message.core.object.ClientInfo;
import vn.com.onesoft.bigfox.io.message.core.tags.CoreTags;
import vn.com.onesoft.bigfox.io.message.annotations.Message;
import vn.com.onesoft.bigfox.io.message.annotations.Property;

/**
 *
 * @author QuanPH
 */
@Message(tag = CoreTags.CS_CLIENT_INFO, name = "CS_CLIENT_INFO", isCore = true)
public class CSClientInfo extends BaseMessage {

    @Property(name = "clientInfo")
    private ClientInfo clientInfo;

    public CSClientInfo() {
        clientInfo = new ClientInfo();
        clientInfo.device = ClientInfo.DEVICE_ANDROID;
        clientInfo.imei = BigFoxContext.imei;

        if (ConnectionManager.getInstance().sessionId.length() == 0) {
            ConnectionManager.getInstance().sessionId = BFUtils.genRandomString(10);
        }
        clientInfo.sessionId = ConnectionManager.getInstance().sessionId;
        clientInfo.version = BigFoxContext.version;
        clientInfo.metadata = "";
    }

}
