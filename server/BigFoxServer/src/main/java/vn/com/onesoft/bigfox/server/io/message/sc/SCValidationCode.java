/*
 * Author: HuongNS
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */
package vn.com.onesoft.bigfox.server.io.message.sc;

import vn.com.onesoft.bigfox.server.io.message.core.MessageOut;
import vn.com.onesoft.bigfox.server.io.message.core.Tags;
import vn.com.onesoft.bigfox.server.io.message.core.annotations.Message;
import vn.com.onesoft.bigfox.server.io.message.core.annotations.Property;

/**
 *
 * @author HuongNS
 */
@Message(tag = Tags.SC_VALIDATION_CODE, name = "SC_VALIDATION_CODE")
public class SCValidationCode extends MessageOut {

    @Property(name = "validationCode")
    private int validationCode;

    public SCValidationCode(int validationCode) {
        this.validationCode = validationCode;
    }

}
