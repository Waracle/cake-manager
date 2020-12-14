package com.waracle.cakemgr.service.impl;


import com.waracle.cakemgr.entity.Cake;
import com.waracle.cakemgr.HibernateUtil;
import com.waracle.cakemgr.service.DaoService;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.persistence.Query;
import java.util.List;

@Service
public class DaoServiceImpl implements DaoService {
    private static final Logger log = LoggerFactory.getLogger(DaoServiceImpl.class);


    @Override
    public Cake add(Cake cake) {

        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
        session.beginTransaction();
        log.debug("persisting cake with title: {}", cake.getTitle());
        session.persist(cake);

        session.getTransaction().commit();
    }
        return cake;
    }

    @Override
    public Cake getCakeByTitle(String title) {
        if (title == null){
            return null;
        }
        Cake savedCake = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("from Cake where title = :title");
            query.setParameter("title", title);
            List resultList = query.getResultList();
            if (!resultList.isEmpty()) {
                savedCake = (Cake) resultList.get(0);
                log.debug("getting cake by title: {}", title);
            }
        }

        return savedCake;
    }


    @Override
    public Cake getCake(Integer id) {
        if (id == null){
            return null;
        }
        Cake savedCake = null;


        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            savedCake = (Cake) session.get(Cake.class, id);
            log.debug("getting cake id: {}", savedCake.getId());
            session.getTransaction().commit();
        }
        return savedCake;
    }

    @Override
    public List<Cake> getAllCakes() {
            List<Cake> cakes = null;
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                cakes = session.createQuery("from Cake").list();
            }

        return cakes;
    }

    @Override
    public void deleteCake(Cake cake) {

        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.delete(cake);
            log.debug("deleting cake id: {}", cake.getId());
            session.getTransaction().commit();
        }
    }

    @Override
    public boolean isCakeExists(Cake cake){
        return getCakeByTitle(cake.getTitle()) !=null;
    }
}
