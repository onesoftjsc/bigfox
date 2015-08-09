
/*
 * Author: HuongNS
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */
package vn.com.onesoft.livetube.io.message.cs;

import io.netty.channel.Channel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import vn.com.onesoft.livetube.io.message.core.MessageIn;
import vn.com.onesoft.livetube.io.message.core.Tags;
import vn.com.onesoft.livetube.io.message.core.annotations.Message;
import vn.com.onesoft.livetube.io.message.objects.Category;
import vn.com.onesoft.livetube.io.message.objects.Video;
import vn.com.onesoft.livetube.io.message.sc.SCRequestListMySchedule;
import vn.com.onesoft.livetube.io.session.BFSessionManager;
import vn.com.onesoft.livetube.main.LiveTubeContext;
import vn.com.onesoft.livetube.main.Main;

/**
 *
 * @author HuongNS
 */
@Message(tag = Tags.CS_REQUEST_LIST_MY_SCHEDULE, name = "CS_REQUEST_LIST_MY_SCHEDULE")
public class CSRequestListMySchedule extends MessageIn {

    @Override
    public void execute(Channel channel) {
        Main.logger.info(this.getClass().getName());
        if (BFSessionManager.getInstance().getSessionByChannel(channel).getdBUser() == null) {
            return;
        }

        if (LiveTubeContext.mapCategoryIdToCategoryLiveUpcoming.isEmpty()) {
            Category categoryExam;
            for (int i = 0; i < 2; i++) {
                categoryExam = new Category(i, "Category " + i);
                for (int j = 0; j < 3; j++) {
                    categoryExam.videos.add(new Video(j, "Video Upcoming" + j, "Huongns" + j, null, 1, true, 2));
                }

                LiveTubeContext.mapCategoryIdToCategoryLiveUpcoming.put(i, categoryExam);
                BFSessionManager.getInstance().sendMessage(channel, new SCRequestListMySchedule(new ArrayList<>(LiveTubeContext.mapCategoryIdToCategoryLiveUpcoming.values())));
                LiveTubeContext.mapCategoryIdToCategoryLiveUpcoming.clear();
            }
        } else {

            int userId = BFSessionManager.getInstance().getSessionByChannel(channel).getdBUser().getId();
            List<Category> listCategoryMySchedule;
            listCategoryMySchedule = new ArrayList<>();
            Iterator<Category> iterator = LiveTubeContext.mapCategoryIdToCategoryLiveUpcoming.values().iterator();
            while (iterator.hasNext()) {
                Category next = iterator.next();
                Category categorySchedule = null;
                for (Video video : next.videos) {
                    if (video.authorId == userId) {
                        if (categorySchedule == null) {
                            categorySchedule = new Category(next.id, next.name);
                        }
                        listCategoryMySchedule.add(next);
                    }
                }
                listCategoryMySchedule.add(categorySchedule);
            }
            BFSessionManager.getInstance().sendMessage(channel, new SCRequestListMySchedule(listCategoryMySchedule));

        }

    }
}
