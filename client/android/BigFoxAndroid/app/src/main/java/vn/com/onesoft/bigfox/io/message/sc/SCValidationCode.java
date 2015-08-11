/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.com.onesoft.bigfox.io.message.sc;

import vn.com.onesoft.bigfox.io.core.ConnectionManager;
import vn.com.onesoft.bigfox.io.message.core.BaseMessage;
import vn.com.onesoft.bigfox.io.message.core.IMessageIn;
import vn.com.onesoft.bigfox.io.message.core.Tags;
import vn.com.onesoft.bigfox.io.message.core.annotations.Message;
import vn.com.onesoft.bigfox.io.message.core.annotations.Property;
import vn.com.onesoft.bigfox.io.message.cs.CSClientInfo;

/**
 *
 * @author Quan
 */
@Message(tag = Tags.SC_VALIDATION_CODE, name = "SC_VALIDATION_CODE")
public class SCValidationCode extends BaseMessage implements IMessageIn {

    @Property(name = "validationCode")
    private int validationCode;

    @Override
    public void execute() {
        ConnectionManager.getInstance().validationCode = this.validationCode;
        ConnectionManager.getInstance().write(new CSClientInfo());
    }

}
