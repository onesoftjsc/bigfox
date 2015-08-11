package vn.com.onesoft.bigfox.io.message.sc;

import vn.com.onesoft.bigfox.io.message.core.BaseMessage;
import vn.com.onesoft.bigfox.io.message.core.Tags;
import vn.com.onesoft.bigfox.io.message.core.annotations.Message;
import vn.com.onesoft.bigfox.io.message.core.annotations.Property;
import vn.com.onesoft.bigfox.io.message.object.Category;

@Message(tag = Tags.SC_LIVE_UPCOMING, name = "SC_REQUEST_LIST_VIDEO_UP_COMING")
public class SCRequestListVideoUpComing extends BaseMessage {

	@Property(name = "categories")
	public Category[] categories;
}
