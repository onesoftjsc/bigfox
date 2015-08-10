/*
 * Author: HuongNS
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */
package vn.com.onesoft.livetube.main;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import org.hibernate.Session;
import org.hibernate.Transaction;
import vn.com.onesoft.livetube.db.DBUser;
import vn.com.onesoft.livetube.db.DBVideoCategory;
import vn.com.onesoft.livetube.db.util.HibernateFactoryUtil;
import vn.com.onesoft.livetube.io.message.objects.Category;

/**
 *
 * @author HuongNS
 */
public final class InitMain {

    public InitMain() {
        initListCategory();
        initListLive();
        initListLiveUpcoming();

    }

    public void initListCategory() {
        Session session = HibernateFactoryUtil.getInstance().getCurrentSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            DBUser dBUser = null;

            List dBVideoCategorys = session.createQuery("FROM DBVideoCategory").list();

            List<Category> categorys = new ArrayList<>();

            Iterator iterator = dBVideoCategorys.iterator();

            while (iterator.hasNext()) {
                DBVideoCategory dBVideoCategory = (DBVideoCategory) iterator.next();
                LiveTubeContext.mapCategoryIdToCategory.put(dBVideoCategory.getId(), new Category(dBVideoCategory.getId(), dBVideoCategory.getName(), "http://192.168.1.172:8080/WebApplicationUpload/livetube/2015/08/06/category_icon" + Math.abs(new Random().nextInt(3)) + ".png"));
            }
            Main.logger.info("Huongns: MyContext.mapCategoryIdToCategory.size() = " + LiveTubeContext.mapCategoryIdToCategory.size());

            tx.commit();
        } catch (Exception ex) {
            Main.logger.error(ex.getMessage(), ex);
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
        }
    }

    private void initListLive() {
    }

    private void initListLiveUpcoming() {
    }

}
