/*
 * Author: HuongNS
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */
package vn.com.onesoft.livetube.io.message.sc;

import vn.com.onesoft.livetube.io.message.core.MessageOut;
import vn.com.onesoft.livetube.io.message.core.Tags;
import vn.com.onesoft.livetube.io.message.core.annotations.Message;

/**
 *
 * @author HuongNS
 */
@Message(tag = Tags.SC_LOGOUT, name = "SC_LOGOUT")
public class SCLogout extends MessageOut {

    public SCLogout() {
    }
}
