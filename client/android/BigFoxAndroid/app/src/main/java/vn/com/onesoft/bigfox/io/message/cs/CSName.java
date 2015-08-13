package vn.com.onesoft.bigfox.io.message.cs;

import vn.com.onesoft.bigfox.io.message.core.BaseMessage;
import vn.com.onesoft.bigfox.io.message.core.Tags;
import vn.com.onesoft.bigfox.io.message.core.annotations.Message;
import vn.com.onesoft.bigfox.io.message.core.annotations.Property;

/**
 * Created by phamquan on 8/13/15.
 */
@Message(tag = Tags.CS_NAME, name = "CS_NAME")
public class CSName extends BaseMessage {

    @Property(name = "msg")
    private String msg;

    public CSName(String msg) {
        this.msg = msg;
    }

}