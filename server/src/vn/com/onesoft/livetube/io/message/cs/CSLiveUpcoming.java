/*
 * Author: HuongNS
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */
package vn.com.onesoft.livetube.io.message.cs;

import io.netty.channel.Channel;
import java.sql.Timestamp;
import java.util.Date;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import vn.com.onesoft.livetube.db.DBVideo;
import vn.com.onesoft.livetube.db.util.HibernateFactoryUtil;
import vn.com.onesoft.livetube.io.message.core.MessageIn;
import vn.com.onesoft.livetube.io.message.core.Tags;

import vn.com.onesoft.livetube.io.message.core.annotations.Message;
import vn.com.onesoft.livetube.io.message.core.annotations.Property;
import vn.com.onesoft.livetube.io.message.objects.Category;
import vn.com.onesoft.livetube.io.message.objects.Video;
import vn.com.onesoft.livetube.io.message.sc.SCLiveUpcoming;
import vn.com.onesoft.livetube.io.session.BFSessionManager;

import vn.com.onesoft.livetube.main.LiveTubeContext;
import vn.com.onesoft.livetube.main.Main;

/**
 *
 * @author HuongNS
 */
@Message(tag = Tags.CS_LIVE_UPCOMING, name = "CS_LIVE_UPCOMING")
public class CSLiveUpcoming extends MessageIn {

    @Property(name = "categoryId")
    private int categoryId;

    @Property(name = "video")
    private Video video;

    @Override
    public void execute(Channel channel) {
        Main.logger.info(this.getClass().getName());

        if (BFSessionManager.getInstance().getSessionByChannel(channel).getdBUser() == null || BFSessionManager.getInstance().getSessionByChannel(channel).getdBUser().getId() != video.authorId) {
            return;
        }

        Category categoryLiveUpcoming;
        if (LiveTubeContext.mapCategoryIdToCategoryLiveUpcoming.isEmpty() || LiveTubeContext.mapCategoryIdToCategoryLiveUpcoming.get(categoryId) == null) {
            categoryLiveUpcoming = LiveTubeContext.mapCategoryIdToCategory.get(categoryId);
            categoryLiveUpcoming.videos.clear();
            categoryLiveUpcoming.videos.add(video);
            LiveTubeContext.mapCategoryIdToCategoryLiveUpcoming.put(categoryId, categoryLiveUpcoming);
        } else {
            categoryLiveUpcoming = LiveTubeContext.mapCategoryIdToCategoryLiveUpcoming.get(categoryId);
            categoryLiveUpcoming.videos.add(video);
        }

        Session currentSession = HibernateFactoryUtil.getInstance().getCurrentSession();
        Transaction beginTransaction = null;

        try {
            beginTransaction = currentSession.beginTransaction();

            DBVideo dBVideo = new DBVideo();

            dBVideo.setVideo_category(categoryId);
            dBVideo.setTitle(video.title);
            dBVideo.setOwnerid(video.authorId);
            dBVideo.setCreated_date(new Timestamp(new Date().getTime()));
            dBVideo.setState(2);// 1: live | 2: upcoming
            dBVideo.setPrivacy(video.privacy);
            dBVideo.setChat(video.chat);
            dBVideo.setDescription(video.description);
            dBVideo.setStart_time(new Timestamp(video.start_time));
            dBVideo.setEnd_time(new Timestamp(video.end_time));
//            dBVideo.setThumbnail(video.thumbnail);

            currentSession.save(dBVideo);

            beginTransaction.commit();
        } catch (Exception e) {
            Main.logger.error(e.getMessage(), e);
            if (beginTransaction != null && beginTransaction.isActive()) {
                beginTransaction.rollback();
            }
        }

        BFSessionManager.getInstance().sendMessage(channel, new SCLiveUpcoming(categoryId, video));

    }

}
