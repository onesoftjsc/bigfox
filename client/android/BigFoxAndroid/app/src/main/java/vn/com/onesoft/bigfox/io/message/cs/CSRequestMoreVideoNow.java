package vn.com.onesoft.bigfox.io.message.cs;

import vn.com.onesoft.bigfox.io.message.core.BaseMessage;
import vn.com.onesoft.bigfox.io.message.core.Tags;
import vn.com.onesoft.bigfox.io.message.core.annotations.Message;
import vn.com.onesoft.bigfox.io.message.core.annotations.Property;

@Message(tag = Tags.CS_REQUEST_LIST_VIDEO_NOW, name = "CS_REQUEST_MORE_VIDEO")
public class CSRequestMoreVideoNow extends BaseMessage {
    @Property(name="categoryId")
    public int categoryId;
    @Property(name="currentVideoCount")
    public int currentVideoCount;
}
