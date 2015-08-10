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

@Message(tag = Tags.SC_LOGIN, name = "SC_LOGIN")
public class SCLogin extends MessageOut {

    @Property(name = "responseCode")
    public int responseCode;

    public SCLogin(int responseCode) {
        super();
        this.responseCode = responseCode;
    }

}
