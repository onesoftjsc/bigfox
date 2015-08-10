
/*
 * Author: HuongNS
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */
package vn.com.onesoft.livetube.io.message.cs;

import io.netty.channel.Channel;
import java.util.ArrayList;
import java.util.List;
import vn.com.onesoft.livetube.io.message.core.MessageIn;

import vn.com.onesoft.livetube.io.message.core.Tags;
import vn.com.onesoft.livetube.io.message.core.annotations.Message;
import vn.com.onesoft.livetube.io.message.objects.Category;
import vn.com.onesoft.livetube.io.message.objects.Video;
import vn.com.onesoft.livetube.io.message.sc.SCRequestListVideoUpComing;
import vn.com.onesoft.livetube.io.session.BFSessionManager;

import vn.com.onesoft.livetube.main.LiveTubeContext;
import vn.com.onesoft.livetube.main.Main;

/**
 *
 * @author HuongNS
 */
@Message(tag = Tags.CS_REQUEST_LIST_VIDEO_UP_COMING, name = "CS_REQUEST_LIST_VIDEO_UP_COMING")
public class CSRequestListVideoUpComing extends MessageIn {

    @Override
    public void execute(Channel channel) {
        Main.logger.info(this.getClass().getName());

        Category categoryExam;

        if (LiveTubeContext.mapCategoryIdToCategoryLiveUpcoming.isEmpty()) {
            //dung de test
            for (int i = 0; i < 2; i++) {
                categoryExam = new Category(i, "Category " + i, "http://192.168.1.172:8080/WebApplicationUpload/livetube/2015/08/06/category_icon"+i+".png");
                for (int j = 0; j < 3; j++) {
                    Video video = new Video(j, "Video Upcoming" + j, "Huongns" + j, 1, true, 2, "Mo ta Video Upcoming" + j);
                    video.thumbnail = "http://192.168.1.172:8080/WebApplicationUpload/livetube/2015/08/06/imageVideo"+j+".jpg";
                    categoryExam.videos.add(video);
                }
                LiveTubeContext.mapCategoryIdToCategoryLiveUpcoming.put(i, categoryExam);
            }

            BFSessionManager.getInstance().sendMessage(channel, new SCRequestListVideoUpComing(new ArrayList<>(LiveTubeContext.mapCategoryIdToCategoryLiveUpcoming.values())));
            LiveTubeContext.mapCategoryIdToCategoryLiveUpcoming.clear();
        } else {
            BFSessionManager.getInstance().sendMessage(channel, new SCRequestListVideoUpComing(new ArrayList<>(LiveTubeContext.mapCategoryIdToCategoryLiveUpcoming.values())));
        }
    }
}
