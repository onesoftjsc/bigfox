/*
 * Author: HuongNS
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */
package vn.com.onesoft.livetube.io.message.sc;

import vn.com.onesoft.livetube.io.message.core.MessageOut;
import vn.com.onesoft.livetube.io.message.core.Tags;
import vn.com.onesoft.livetube.io.message.core.annotations.Message;
import vn.com.onesoft.livetube.io.message.core.annotations.Property;
import vn.com.onesoft.livetube.io.message.objects.Video;

/**
 *
 * @author HuongNS
 */
@Message(tag = Tags.SC_LIVE_UPCOMING, name = "SC_LIVE_UPCOMING")
public class SCLiveUpcoming extends MessageOut {

    @Property(name = "categoryId")
    private int categoryId;

    @Property(name = "video")
    private Video video;

    public SCLiveUpcoming(int categoryId, Video video) {
        super();
        this.categoryId = categoryId;
        this.video = video;
    }

}
