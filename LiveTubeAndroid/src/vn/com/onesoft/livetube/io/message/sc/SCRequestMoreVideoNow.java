package vn.com.onesoft.livetube.io.message.sc;

import vn.com.onesoft.livetube.io.message.core.BaseMessage;
import vn.com.onesoft.livetube.io.message.core.Tags;
import vn.com.onesoft.livetube.io.message.core.annotations.Message;
import vn.com.onesoft.livetube.io.message.core.annotations.Property;
import vn.com.onesoft.livetube.io.message.object.Video;

@Message(tag = Tags.SC_LIVE_NOW, name = "SC_REQUEST_MORE_VIDEO_NOW")
public class SCRequestMoreVideoNow extends BaseMessage {

    @Property(name = "videos")
    public Video[] videos;
}
