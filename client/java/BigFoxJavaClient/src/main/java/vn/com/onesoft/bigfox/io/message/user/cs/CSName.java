/*
 * Author: QuanPH
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */

package vn.com.onesoft.bigfox.io.message.user.cs;

import vn.com.onesoft.bigfox.io.message.annotations.Message;
import vn.com.onesoft.bigfox.io.message.annotations.Property;
import vn.com.onesoft.bigfox.io.message.base.MessageOut;
import vn.com.onesoft.bigfox.io.message.user.tags.Tags;


/**
 *
 * @author QuanPH
 */
@Message(tag = Tags.CS_NAME, name = "CS_NAME")
public class CSName extends MessageOut {

    @Property(name = "msg")
    private String msg;

    public CSName(String msg) {
        this.msg = msg;
    }

}
