/*
 * Author: QuanPH
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */
package vn.com.onesoft.bigfox.server.io.message.objects;

import vn.com.onesoft.bigfox.server.io.message.core.annotations.Property;

/**
 *
 * @author QuanPH
 */
public class ClientInfo {

    @Property(name = "device")
    public String device; // ios, android, wp
    @Property(name = "imei")
    public String imei;
    @Property(name = "version")
    public int version;
    @Property(name = "sessionId")
    public String sessionId = ""; //String duy nhất đinh danh session, lưu dynamic trên Ram, không lưu ổ cưng
    @Property(name = "metadata")
    public String metadata = ""; 
    
    public final static String DEVICE_IOS = "ios";
    public final static String DEVICE_ANDROID = "android";
    public final static String DEVICE_WP = "wp";
    public final static String DEVICE_WEB = "web";
}
