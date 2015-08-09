
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
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import vn.com.onesoft.livetube.db.DBUserSubscribe;
import vn.com.onesoft.livetube.db.DBVideo;
import vn.com.onesoft.livetube.db.util.HibernateFactoryUtil;
import vn.com.onesoft.livetube.io.message.core.BigFoxUtils;
import vn.com.onesoft.livetube.io.message.core.MessageIn;
import vn.com.onesoft.livetube.io.message.core.Tags;
import vn.com.onesoft.livetube.io.message.core.annotations.Message;
import vn.com.onesoft.livetube.io.message.objects.Video;
import vn.com.onesoft.livetube.io.message.sc.SCRequestListSubcriptionVideo;
import vn.com.onesoft.livetube.io.session.BFSessionManager;
import vn.com.onesoft.livetube.main.Main;

/**
 *
 * @author HuongNS
 */
@Message(tag = Tags.CS_REQUEST_LIST_SUBCRIPTION_VIDEO, name = "CS_REQUEST_LIST_SUBCRIPTION_VIDEO")
public class CSRequestListSubcriptionVideo extends MessageIn {

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

            List listDBUserSubscribes = currentSession.createQuery("FROM DBUserSubscribe WHERE userid = :userid")
                    .setParameter("userid", userId)
                    .list();

            Iterator iteratorDBUserSubscribes = listDBUserSubscribes.iterator();
            
            List<Integer> listOwnerId = new ArrayList<>();
            
            while (iteratorDBUserSubscribes.hasNext()) {
                DBUserSubscribe next = (DBUserSubscribe) iteratorDBUserSubscribes.next();
                int subid = next.getSubid();
                listOwnerId.add(subid);
            }

            List listDBVideos = currentSession.createQuery("FROM DBVideo WHERE ownerid in (:in)")
                    .setParameterList("in", listOwnerId)
                    .list();
            
            Iterator iteratorDBVideos = listDBVideos.iterator();

            List<Video> videos = new ArrayList<>();

            while (iteratorDBVideos.hasNext()) {
                DBVideo next = (DBVideo) iteratorDBVideos.next();
                Video video = new Video(next.getId(), next.getTitle());
                videos.add(video);
            }

            BFSessionManager.getInstance().sendMessage(channel, new SCRequestListSubcriptionVideo(videos));

            beginTransaction.commit();
        } catch (Exception e) {
            Main.logger.error(e.getMessage(), e);
            if (currentSession != null && currentSession.isOpen()) {
                if (beginTransaction != null) {
                    beginTransaction.rollback();
                }
            }
        }

    }
}
