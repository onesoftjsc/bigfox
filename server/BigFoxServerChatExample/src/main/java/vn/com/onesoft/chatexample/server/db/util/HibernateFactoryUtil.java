package vn.com.onesoft.chatexample.server.db.util;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

/**
 *
 * @author Quan
 */
public class HibernateFactoryUtil {

    /**
     * The single instance of hibernate SessionFactory
     */
    private static org.hibernate.SessionFactory sessionFactory;

    /**
     * disable contructor to guaranty a single instance
     */
    private HibernateFactoryUtil() {
    }

    static {
        File file = new File("hibernate.cfg.xml");
        String fname = file.getAbsolutePath();
        sessionFactory = new AnnotationConfiguration().configure(file).buildSessionFactory();
    }

    public static void reloadConfig() {
        File file = new File("hibernate.cfg.xml");
        sessionFactory = new AnnotationConfiguration().configure(file).buildSessionFactory();
    }

    public static SessionFactory getInstance() {
        return sessionFactory;
    }

    public static void reset() {
        sessionFactory.close();
        File file = new File("hibernate.cfg.xml");
        sessionFactory = new AnnotationConfiguration().configure(file).buildSessionFactory();
    }

    /**
     * Opens a session and will not bind it to a session context
     *
     * @return the session
     */
    public Session openSession() {
        return sessionFactory.openSession();
    }

    /**
     * Returns a session from the session context. If there is no session in the
     * context it opens a session, stores it in the context and returns it. This
     * factory is intended to be used with a hibernate.cfg.xml including the
     * following property <property
     * name="current_session_context_class">thread</property> This would return
     * the current open session or if this does not exist, will create a new
     * session
     *
     * @return the session
     */
    public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    /**
     * closes the session factory
     */
    public static void close() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
        sessionFactory = null;
    }
}
