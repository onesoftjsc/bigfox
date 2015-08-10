
/*
 * Author: HuongNS
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */
package vn.com.onesoft.livetube.io.message.cs;

import com.google.common.collect.MapMaker;
import io.netty.channel.Channel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.SerializationUtils;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import vn.com.onesoft.livetube.db.DBVideo;
import vn.com.onesoft.livetube.db.util.HibernateFactoryUtil;
import vn.com.onesoft.livetube.io.message.core.MessageIn;
import vn.com.onesoft.livetube.io.message.core.Tags;
import vn.com.onesoft.livetube.io.message.core.annotations.Message;
import vn.com.onesoft.livetube.io.message.objects.Category;
import vn.com.onesoft.livetube.io.message.objects.Video;
import vn.com.onesoft.livetube.io.message.sc.SCRequestListMyVideo;
import vn.com.onesoft.livetube.io.session.BFSessionManager;
import vn.com.onesoft.livetube.main.LiveTubeContext;

import vn.com.onesoft.livetube.main.Main;

/**
 *
 * @author HuongNS
 */
@Message(tag = Tags.CS_REQUEST_LIST_MY_VIDEO, name = "CS_REQUEST_LIST_MY_VIDEO")
public class CSRequestListMyVideo extends MessageIn {

    @Override
    public void execute(Channel channel) {
        Main.logger.info(this.getClass().getName());
        if (BFSessionManager.getInstance().getSessionByChannel(channel).getdBUser() == null) {
            return;
        }

        int userId = BFSessionManager.getInstance().getSessionByChannel(channel).getdBUser().getId();

        Session currentSession = HibernateFactoryUtil.getInstance().getCurrentSession();
        Transaction beginTransaction = null;
        try {
            beginTransaction = currentSession.beginTransaction();

            List<DBVideo> listDbVideo;
            listDbVideo = currentSession.createQuery("FROM DBVideo where ownerid = :ownerid")
                    .setParameter("ownerid", userId).list();

            if (listDbVideo.size() > 0) {
                Collection<Category> categorys = LiveTubeContext.mapCategoryIdToCategory.values();
                Iterator<Category> iterator = categorys.iterator();
                while (iterator.hasNext()) {
                    Category category;
                    category = iterator.next();
                    Video video;
                    category.videos.clear();
                    for (DBVideo dBVideo : listDbVideo) {
                        if (category.id == dBVideo.getVideo_category()) {
                            video = new Video();
                            video.id = dBVideo.getId();
                            video.title = dBVideo.getTitle();
                            video.authorId = dBVideo.getOwnerid();
                            video.description = dBVideo.getDescription();
                            category.videos.add(video);
                        }
                    }
                }
                BFSessionManager.getInstance().sendMessage(channel, new SCRequestListMyVideo(new ArrayList<>(LiveTubeContext.mapCategoryIdToCategory.values())));
            }

            beginTransaction.commit();
        } catch (Exception e) {
            if (currentSession != null && currentSession.isOpen()) {
                beginTransaction.rollback();
            }
        }

    }
}
