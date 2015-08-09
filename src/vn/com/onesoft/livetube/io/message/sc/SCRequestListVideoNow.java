package vn.com.onesoft.livetube.io.message.sc;

import java.util.List;

import vn.com.onesoft.livetube.io.message.core.BaseMessage;
import vn.com.onesoft.livetube.io.message.core.Tags;
import vn.com.onesoft.livetube.io.message.core.annotations.Message;
import vn.com.onesoft.livetube.io.message.core.annotations.Property;
import vn.com.onesoft.livetube.io.message.object.Category;

@Message(tag = Tags.SC_REQUEST_LIST_VIDEO_NOW, name = "SC_REQUEST_LIST_VIDEO_NOW")
public class SCRequestListVideoNow extends BaseMessage {

    @Property(name = "categories")
    public List<Category> categories;
}
