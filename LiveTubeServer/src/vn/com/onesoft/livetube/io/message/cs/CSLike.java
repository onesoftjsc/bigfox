
/*
 * Author: HuongNS
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */
package vn.com.onesoft.livetube.io.message.cs;

import io.netty.channel.Channel;
import org.hibernate.Session;
import org.hibernate.Transaction;
import vn.com.onesoft.livetube.db.DBUser;
import vn.com.onesoft.livetube.db.util.HibernateFactoryUtil;
import vn.com.onesoft.livetube.io.message.core.MessageIn;
import vn.com.onesoft.livetube.io.message.core.Tags;
import vn.com.onesoft.livetube.io.message.core.annotations.Message;
import vn.com.onesoft.livetube.io.message.core.annotations.Property;
import vn.com.onesoft.livetube.io.session.BFSessionManager;
import vn.com.onesoft.livetube.main.Main;
import vn.com.onesoft.livetube.util.LiveTubeUtil;

/**
 *
 * @author HuongNS
 */
@Message(tag = Tags.CS_LIKE, name = "CS_LIKE")
public class CSLike extends MessageIn {

    @Property(name = "videoId")
    public int videoId;
    @Property(name = "action")
    public int action; // action = 0: unlike, action =1: like
    public static final int ACTION_UNLIKE = 0;
    public static final int ACTION_LIKE = 1;

    @Override
    public void execute(Channel channel) {
        Main.logger.info(this.getClass().getName());
        if (BFSessionManager.getInstance().getSessionByChannel(channel).getdBUser() == null) {
            return;
        }
        Session session = HibernateFactoryUtil.getInstance().getCurrentSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            DBUser dBUser = null;
            //test
            System.out.println("");
            tx.commit();
        } catch (Exception ex) {
            Main.logger.error(ex.getMessage(), ex);
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
        }
    }
}
