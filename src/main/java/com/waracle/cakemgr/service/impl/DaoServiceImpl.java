package com.waracle.cakemgr.service.impl;


import com.waracle.cakemgr.CakeEntity;
import com.waracle.cakemgr.HibernateUtil;
import com.waracle.cakemgr.service.DaoService;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DaoServiceImpl implements DaoService {

    @Override
    public void add(CakeEntity cake) {
        HibernateUtil hibernateUtil = new HibernateUtil();

        Session session = hibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.persist(cake);
            System.out.println("adding cake entity");
            session.getTransaction().commit();
        } catch (ConstraintViolationException ex) {
            System.out.println(ex);
        }
        session.close();

    }

    @Override
    public CakeEntity getCake(Integer id) {
        if (id == null){
            return null;
        }
        CakeEntity savedCake = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            savedCake = (CakeEntity) session.get(CakeEntity.class, id);
            System.out.println("adding cake entity");
            session.getTransaction().commit();
        } catch (ConstraintViolationException ex) {
            System.out.println(ex);
        }
        session.close();
        return savedCake;
    }

    @Override
    public List<CakeEntity> getAllCakes() {
        return null;
    }
}
