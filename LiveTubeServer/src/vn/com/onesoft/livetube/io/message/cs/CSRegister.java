
/*
 * Author: HuongNS
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */
package vn.com.onesoft.livetube.io.message.cs;

import io.netty.channel.Channel;
import vn.com.onesoft.livetube.io.message.core.MessageIn;

import vn.com.onesoft.livetube.io.message.core.Tags;
import vn.com.onesoft.livetube.io.message.core.annotations.Message;
import vn.com.onesoft.livetube.io.message.core.annotations.Property;
import vn.com.onesoft.livetube.main.BFUtils;
import vn.com.onesoft.livetube.main.LiveTubeContext;
import vn.com.onesoft.livetube.main.Main;
import vn.com.onesoft.livetube.util.LiveTubeUtil;

/**
 *
 * @author HuongNS
 */
@Message(tag = Tags.CS_REGISTER, name = "CS_REGISTER")
public class CSRegister extends MessageIn {

    @Property(name = "phone")
    public String phone;

    @Override
    public void execute(Channel channel) {
        Main.logger.info(this.getClass().getName());
        String code = LiveTubeUtil.getRandomId(4);

        LiveTubeContext.mapPhoneToCode.put(phone, code);

        BFUtils.sendSMSCode(phone, code);
    }
}
