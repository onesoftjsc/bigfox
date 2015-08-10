/*
 * Author: HuongNS
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */
package vn.com.onesoft.livetube.io.message.sc;

import java.util.Collection;
import vn.com.onesoft.livetube.io.message.core.MessageOut;
import vn.com.onesoft.livetube.io.message.core.Tags;
import vn.com.onesoft.livetube.io.message.core.annotations.Message;
import vn.com.onesoft.livetube.io.message.core.annotations.Property;
import vn.com.onesoft.livetube.io.message.objects.Video;

/**
 *
 * @author HuongNS
 */
@Message(tag = Tags.SC_REQUEST_LIST_VIDEO_HISTORY, name = "SC_REQUEST_LIST_VIDEO_HISTORY")
public class SCRequestListVideoHistory extends MessageOut {

    @Property(name = "videos")
    public Collection<Video> videos;
    @Property(name = "responseCode")
    public int responseCode;

    public SCRequestListVideoHistory(Collection<Video> videos, int responseCode) {
        super();
        this.videos = videos;
        this.responseCode = responseCode;
    }
    
    
}
