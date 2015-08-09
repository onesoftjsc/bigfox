package vn.com.onesoft.livetube.io.message.cs;

import vn.com.onesoft.livetube.io.message.core.BaseMessage;
import vn.com.onesoft.livetube.io.message.core.Tags;
import vn.com.onesoft.livetube.io.message.core.annotations.Message;
import vn.com.onesoft.livetube.io.message.core.annotations.Property;

@Message(tag = Tags.CS_LIVE_UPCOMING, name = "CS_REQUEST_MORE_VIDEO")
public class CSRequestMoreVideoUpComing extends BaseMessage {
    @Property(name="categoryId")
    public int categoryId;
    @Property(name="currentVideoCount")
    public int currentVideoCount;
}
