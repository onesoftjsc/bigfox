/*
 * Author: QuanPH
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */
package vn.com.onesoft.livetube.io.message.cs;

import vn.com.onesoft.livetube.io.core.BFUtils;
import vn.com.onesoft.livetube.io.core.ConnectionManager;
import vn.com.onesoft.livetube.io.core.LivetubeContext;
import vn.com.onesoft.livetube.io.core.PingThreadManager;
import vn.com.onesoft.livetube.io.message.core.BaseMessage;
import vn.com.onesoft.livetube.io.message.object.ClientInfo;
import vn.com.onesoft.livetube.io.message.core.Tags;
import vn.com.onesoft.livetube.io.message.core.annotations.Message;
import vn.com.onesoft.livetube.io.message.core.annotations.Property;

/**
 *
 * @author QuanPH
 */
@Message(tag = Tags.CS_CLIENT_INFO, name = "CS_CLIENT_INFO")
public class CSClientInfo extends BaseMessage {

    @Property(name = "clientInfo")
    private ClientInfo clientInfo;

    public CSClientInfo() {
        clientInfo = new ClientInfo();
        clientInfo.device = ClientInfo.DEVICE_ANDROID;
        clientInfo.imei = LivetubeContext.imei;

        if (ConnectionManager.getInstance().sessionId.length() == 0) {
            ConnectionManager.getInstance().sessionId = BFUtils.genRandomString(10);
        }
        clientInfo.sessionId = ConnectionManager.getInstance().sessionId;
        clientInfo.version = LivetubeContext.version;
        clientInfo.metadata = "";
    }

}
