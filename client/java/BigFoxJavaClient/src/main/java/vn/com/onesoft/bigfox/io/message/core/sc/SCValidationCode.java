package vn.com.onesoft.bigfox.io.message.core.sc;

/*
 * Author: HuongNS
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */


import io.netty.channel.Channel;
import vn.com.onesoft.bigfox.io.core.session.ConnectionManager;
import vn.com.onesoft.bigfox.io.message.annotations.Message;
import vn.com.onesoft.bigfox.io.message.annotations.Property;
import vn.com.onesoft.bigfox.io.message.base.MessageIn;
import vn.com.onesoft.bigfox.io.message.core.cs.CSClientInfo;
import vn.com.onesoft.bigfox.io.message.core.tags.CoreTags;

/**
 *
 * @author HuongNS
 */
@Message(tag = CoreTags.SC_VALIDATION_CODE, name = "SC_VALIDATION_CODE", isCore = true)
public class SCValidationCode extends MessageIn {

    @Property(name = "validationCode")
    private int validationCode;

    @Override
    public void execute(Channel channel) {
        ConnectionManager.getInstance().setValidationCode(validationCode);
        ConnectionManager.getInstance().write(new CSClientInfo());
    }

}
