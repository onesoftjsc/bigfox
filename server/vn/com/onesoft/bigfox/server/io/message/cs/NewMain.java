/*
 * Author: HuongNS
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */
package vn.com.onesoft.livetube.io.message.cs;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import vn.com.onesoft.livetube.db.util.HibernateFactoryUtil;
import vn.com.onesoft.livetube.main.Main;

/**
 *
 * @author HuongNS
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        int userId = 1;

        Session currentSession = HibernateFactoryUtil.getInstance().getCurrentSession();
        Transaction beginTransaction = null;
        try {
            beginTransaction = currentSession.beginTransaction();

            List listDBUserSubscribes = currentSession.createQuery("FROM DBUserSubscribe WHERE userid = :userid")
                    .setParameter("userid", userId)
                    .list();

            Iterator iteratorDBUserSubscribes = listDBUserSubscribes.iterator();

            beginTransaction.commit();
        } catch (Exception e) {
            Main.logger.error(e.getMessage(), e);
            if (currentSession != null && currentSession.isOpen()) {
                if (beginTransaction != null) {
                    beginTransaction.rollback();
                }
            }
        }
        for (int i = 0; i < 100; i++) {
            System.out.println("" + RandNum());
        }

    }

    public static Random numGen = new Random();

    public static int RandNum() {
        int rand = Math.abs(numGen.nextInt(100));

        return rand;
    }

}
