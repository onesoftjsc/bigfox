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
import vn.com.onesoft.livetube.io.message.sc.SCLiveNow;
import vn.com.onesoft.livetube.io.session.BFSessionManager;

import vn.com.onesoft.livetube.main.LiveTubeContext;
import vn.com.onesoft.livetube.main.Main;

/**
 *
 * @author HuongNS
 */
@Message(tag = Tags.CS_LIVE_NOW, name = "CS_LIVE_NOW")
public class CSLiveNow extends MessageIn {

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

        Category categoryLive;
        if (LiveTubeContext.mapCategoryIdToCategoryLive.isEmpty() || LiveTubeContext.mapCategoryIdToCategoryLive.get(categoryId) == null) {
            categoryLive = LiveTubeContext.mapCategoryIdToCategory.get(categoryId);
            categoryLive.videos.clear();
            categoryLive.videos.add(video);
            LiveTubeContext.mapCategoryIdToCategoryLive.put(categoryId, categoryLive);

        } else {
            categoryLive = LiveTubeContext.mapCategoryIdToCategoryLive.get(categoryId);
            categoryLive.videos.add(video);
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
            dBVideo.setState(1);// 1: live | 2: upcoming
            dBVideo.setPrivacy(video.privacy);
            dBVideo.setChat(video.chat);
            dBVideo.setDescription(video.description);
            dBVideo.setStart_time(new Timestamp(new Date().getTime()));
//            dBVideo.setThumbnail(video.thumbnail);

            currentSession.save(dBVideo);

            beginTransaction.commit();
        } catch (Exception e) {
            Main.logger.error(e.getMessage(), e);
            if (beginTransaction != null && beginTransaction.isActive()) {
                beginTransaction.rollback();
            }
        }

        BFSessionManager.getInstance().sendMessage(channel, new SCLiveNow(categoryId, video));

    }
}
