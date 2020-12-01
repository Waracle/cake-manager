package com.waracle.cakemgr.controller;

import com.waracle.cakemgr.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class HibernateUtilTest {
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

}
