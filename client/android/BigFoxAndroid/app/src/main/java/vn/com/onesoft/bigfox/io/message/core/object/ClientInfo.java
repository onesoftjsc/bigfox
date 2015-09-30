/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.com.onesoft.bigfox.io.message.core.object;

import vn.com.onesoft.bigfox.io.message.annotations.Property;

/**
 *
 * @author phamquan
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
    @Property(name="zone")
    public String zone = "";

    public final static String DEVICE_IOS = "ios";
    public final static String DEVICE_ANDROID = "android";
    public final static String DEVICE_WP = "wp";
    public final static String DEVICE_WEB = "web";
}
