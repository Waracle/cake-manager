package com.waracle.cakemgr.service.impl;


import com.waracle.cakemgr.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;


public class DaoServiceImplTest {

    private static SessionFactory sessionFactory;
    private Session session;

    @BeforeAll
    public static void setup() {
        sessionFactory = HibernateUtil.getSessionFactory();
        System.out.println("SessionFactory created");
    }

    @AfterAll
    public static void tearDown() {
        if (sessionFactory != null) sessionFactory.close();
        System.out.println("SessionFactory destroyed");
    }

    @BeforeEach
    public void openSession() {
        session = sessionFactory.openSession();
        System.out.println("Session created");
    }

    @AfterEach
    public void closeSession() {
        if (session != null) session.close();
        System.out.println("Session closed\n");
    }

    @Test
    public void testAddCake_SUCCESS(){

    }

    @Test
    public void testAddCake_FAIL(){

    }

    @Test
    public void testListCakes_SUCCESS(){

    }

    @Test
    public void testListCakes_FAIL(){

    }

    @Test
    public void testGetCake_SUCCESS(){

    }

    @Test
    public void testGetCake_FAIL(){

    }

//    @Override
//    public void add(CakeEntity cake) {
//
//        Session session = HibernateUtil.getSessionFactory().openSession();
//        try {
//            session.beginTransaction();
//            session.persist(cake);
//            System.out.println("adding cake entity");
//            session.getTransaction().commit();
//        } catch (ConstraintViolationException ex) {
//            System.out.println(ex);
//        }
//        session.close();
//
//    }
//
//    @Override
//    public CakeEntity getCake(Integer id) {
//        if (id == null){
//            return null;
//        }
//        CakeEntity savedCake = null;
//        Session session = HibernateUtil.getSessionFactory().openSession();
//        try {
//            session.beginTransaction();
//            savedCake = (CakeEntity) session.get(CakeEntity.class, id);
//            System.out.println("adding cake entity");
//            session.getTransaction().commit();
//        } catch (ConstraintViolationException ex) {
//            System.out.println(ex);
//        }
//        session.close();
//        return savedCake;
//    }
//
//    @Override
//    public List<CakeEntity> getAllCakes() {
//        List<CakeEntity> cakes = null;
//        try(Session session = HibernateUtil.getSessionFactory().openSession()){
//            cakes = session.createQuery("from Cake").list();
//        }
//        return cakes;
//    }

}
