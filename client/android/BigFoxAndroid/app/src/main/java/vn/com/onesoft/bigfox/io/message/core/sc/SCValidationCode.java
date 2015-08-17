/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.com.onesoft.bigfox.io.message.core.sc;

import vn.com.onesoft.bigfox.io.core.ConnectionManager;
import vn.com.onesoft.bigfox.io.message.core.base.BaseMessage;
import vn.com.onesoft.bigfox.io.message.core.base.IMessageIn;
import vn.com.onesoft.bigfox.io.message.core.base.CoreTags;
import vn.com.onesoft.bigfox.io.message.core.annotations.Message;
import vn.com.onesoft.bigfox.io.message.core.annotations.Property;
import vn.com.onesoft.bigfox.io.message.core.cs.CSClientInfo;

/**
 *
 * @author Quan
 */
@Message(tag = CoreTags.SC_VALIDATION_CODE, name = "SC_VALIDATION_CODE", isCore = true)
public class SCValidationCode extends BaseMessage implements IMessageIn {

    @Property(name = "validationCode")
    private int validationCode;

    @Override
    public void execute() {
        ConnectionManager.getInstance().validationCode = this.validationCode;
        ConnectionManager.getInstance().write(new CSClientInfo());
    }

}
