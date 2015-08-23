package vn.com.onesoft.bigfox.io.message.user.cs;

import vn.com.onesoft.bigfox.io.message.BaseMessage;
import vn.com.onesoft.bigfox.io.message.annotations.Message;
import vn.com.onesoft.bigfox.io.message.annotations.Property;
import vn.com.onesoft.bigfox.io.message.user.tags.Tags;

/**
 * Created by phamquan on 8/19/15.
 */
@Message(tag = Tags.CS_BIGDATA, name = "CS_BIGDATA")
public class CSBigData extends BaseMessage{

    @Property(name = "data")
    private byte[] data;

    public CSBigData() {
    }


    public CSBigData(byte[] data) {
        this.data = data;
    }
}
