/*
 * Author: HuongNS
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */
package vn.com.onesoft.livetube.io.message.sc;

import vn.com.onesoft.livetube.io.message.core.MessageOut;
import vn.com.onesoft.livetube.io.message.core.Tags;
import vn.com.onesoft.livetube.io.message.core.annotations.Message;
import vn.com.onesoft.livetube.io.message.core.annotations.Property;

/**
 *
 * @author HuongNS
 */

@Message(tag = Tags.SC_REGISTER, name = "SC_REGISTER")
public class SCRegister extends MessageOut {

    @Property(name = "responseCode")
    public int responseCode;

    @Property(name = "phone")
    public String phone;
    
    public SCRegister(int responseCode, String phone) {
        super();
        this.responseCode = responseCode;
        this.phone = phone;
    }
    
    
    

}
