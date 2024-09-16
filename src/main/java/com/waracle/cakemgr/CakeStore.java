package com.waracle.cakemgr;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class CakeStore {

    private static final Logger LOGGER = LoggerFactory.getLogger(CakeStore.class);

    public List<Cake> allCakes() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createCriteria(Cake.class).list();
        } finally {
            session.close();
        }
    }

    public void store(final Cake cake) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        store(cake, session);
    }

    Cake store(final Cake cake, final Session session) {
        try {
            String title = cake.getTitle();
            List existingCakes = session.createCriteria(Cake.class).add(Restrictions.eq("title", title)).list();
            Cake cakeToStore = cake;
            if (existingCakes.size() > 0) {
                Cake existingCake = (Cake)existingCakes.get(0);
                LOGGER.info("found existing cake with matching title: {}. Updating with new details", title);
                cakeToStore = existingCake;
                cakeToStore.setTitle(cake.getTitle());
                cakeToStore.setDescription(cake.getDescription());
                cakeToStore.setImage(cake.getImage());
            }
            session.beginTransaction();
            session.persist(cakeToStore);
            session.getTransaction().commit();
            LOGGER.info("cake stored. id: {}, title", cakeToStore.getCakeId(), title);
        } finally {
            session.close();
        }
        return cake;
    }
}
