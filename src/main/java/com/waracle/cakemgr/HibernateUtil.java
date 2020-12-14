package com.waracle.cakemgr;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class HibernateUtil {
    private static final Logger log = LoggerFactory.getLogger(HibernateUtil.class);


    private static SessionFactory sessionFactory;

    private static SessionFactory buildSessionFactory() {
        try {
            if (sessionFactory == null) {
                Configuration configuration = new Configuration().configure(HibernateUtil.class.getResource("/hibernate.cfg.xml"));
                StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder();
                serviceRegistryBuilder.applySettings(configuration.getProperties());
                sessionFactory = new Configuration().configure().buildSessionFactory();
            }
            return sessionFactory;
        } catch (Throwable ex) {
            log.error("Initial SessionFactory creation failed. {}",ex);
            throw new ExceptionInInitializerError(ex);
        }
    }


    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null ) {
            sessionFactory = buildSessionFactory();
        }
        return sessionFactory;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }

}
