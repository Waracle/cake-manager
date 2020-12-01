package com.waracle.cakemgr.service.impl;


import com.waracle.cakemgr.Cake;
import com.waracle.cakemgr.HibernateUtil;
import com.waracle.cakemgr.service.DaoService;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DaoServiceImpl implements DaoService {

    private HibernateUtil hibernateUtil = new HibernateUtil();

    @Override
    public void add(Cake cake) {

        Session session = hibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            System.out.println("before adding cake entity");
            System.out.println("before title cake entity: "+ cake.getTitle());
            System.out.println("before desc cake entity: " + cake.getDesc());
            System.out.println("before image cake entity: " + cake.getImage());
            session.persist(cake);
            System.out.println("adding cake entity");
            session.getTransaction().commit();
        } catch (ConstraintViolationException ex) {
            System.out.println(ex);
        }
        session.close();

    }

    @Override
    public Cake getCake(Integer id) {
        if (id == null){
            return null;
        }
        Cake savedCake = null;
        Session session = hibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            savedCake = (Cake) session.get(Cake.class, id);
            System.out.println("adding cake entity");
            session.getTransaction().commit();
        } catch (ConstraintViolationException ex) {
            System.out.println(ex);
        }
        session.close();
        return savedCake;
    }

    @Override
    public List<Cake> getAllCakes() {
        List<Cake> cakes = null;
        try(Session session = hibernateUtil.getSessionFactory().openSession()){
            cakes = session.createQuery("from Cake").list();
        }
        return cakes;
    }
}
