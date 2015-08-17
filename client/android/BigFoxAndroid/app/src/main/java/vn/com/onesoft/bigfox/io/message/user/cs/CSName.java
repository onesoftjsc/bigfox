package vn.com.onesoft.bigfox.io.message.user.cs;

import vn.com.onesoft.bigfox.io.message.core.base.BaseMessage;
import vn.com.onesoft.bigfox.io.message.core.base.CoreTags;
import vn.com.onesoft.bigfox.io.message.core.annotations.Message;
import vn.com.onesoft.bigfox.io.message.core.annotations.Property;
import vn.com.onesoft.bigfox.io.message.user.Tags;

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