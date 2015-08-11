package vn.com.onesoft.bigfox.io.message.sc;

import vn.com.onesoft.bigfox.io.message.core.BaseMessage;
import vn.com.onesoft.bigfox.io.message.core.Tags;
import vn.com.onesoft.bigfox.io.message.core.annotations.Message;
import vn.com.onesoft.bigfox.io.message.core.annotations.Property;
import vn.com.onesoft.bigfox.io.message.object.Video;

@Message(tag = Tags.SC_LIVE_NOW, name = "SC_REQUEST_MORE_VIDEO_NOW")
public class SCRequestMoreVideoNow extends BaseMessage {

    @Property(name = "videos")
    public Video[] videos;
}
