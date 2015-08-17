package vn.com.onesoft.bigfox.server.io.message.core.sc;

/*
 * Author: HuongNS
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */


import vn.com.onesoft.bigfox.server.io.messaannotationsons.Property;
import vn.com.onesoft.bigfox.server.io.message.annotations.Message;
import vn.com.onesoft.bigfox.server.io.message.base.MessageOut;
import vn.com.onesoft.bigfox.server.io.message.core.tags.CoreTags;

/**
 *
 * @author HuongNS
 */
@Message(tag = CoreTags.SC_VALIDATION_CODE, name = "SC_VALIDATION_CODE", isCore = true)
public class SCValidationCode extends MessageOut {

    @Property(name = "validationCode")
    private int validationCode;

    public SCValidationCode(int validationCode) {
        this.validationCode = validationCode;
    }

}
