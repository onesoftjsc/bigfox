/*
 * Author: HuongNS
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */
package vn.com.onesoft.livetube.io.message.sc;

import java.util.List;
import vn.com.onesoft.livetube.io.message.core.MessageOut;
import vn.com.onesoft.livetube.io.message.core.Tags;
import vn.com.onesoft.livetube.io.message.core.annotations.Message;
import vn.com.onesoft.livetube.io.message.core.annotations.Property;
import vn.com.onesoft.livetube.io.message.objects.Video;

/**
 *
 * @author HuongNS
 */
@Message(tag = Tags.SC_REQUEST_LIST_SUBCRIPTION_VIDEO, name = "SC_REQUEST_LIST_SUBCRIPTION_VIDEO")
public class SCRequestListSubcriptionVideo extends MessageOut {

    @Property(name = "videos")
    public List<Video> videos;

    /**
     *
     * @param videos
     */
    public SCRequestListSubcriptionVideo(List<Video> videos) {
        super();
        this.videos = videos;
    }
}
