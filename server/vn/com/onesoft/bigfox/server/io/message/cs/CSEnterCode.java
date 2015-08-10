
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
import vn.com.onesoft.livetube.io.message.sc.SCEnterCode;
import vn.com.onesoft.livetube.io.session.BFSessionManager;
import vn.com.onesoft.livetube.main.LiveTubeContext;
import vn.com.onesoft.livetube.main.Main;

/**
 *
 * @author HuongNS
 */
@Message(tag = Tags.CS_ENTER_CODE, name = "CS_ENTER_CODE")
public class CSEnterCode extends MessageIn {

    @Property(name = "phone")
    public String phone;
    @Property(name = "code")
    public String code;

    @Override
    public void execute(Channel channel) {
        Main.logger.info(this.getClass().getName());
        String codeConfirm = LiveTubeContext.mapPhoneToCode.get(phone);

        if (codeConfirm.equals(code)) {
            BFSessionManager.getInstance().sendMessage(channel, new SCEnterCode(1));
        } else {
            BFSessionManager.getInstance().sendMessage(channel, new SCEnterCode(0));
        }
    }
}
