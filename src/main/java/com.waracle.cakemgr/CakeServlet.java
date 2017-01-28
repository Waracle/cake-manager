package com.waracle.cakemgr;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet({"", "/cakes"})
public class CakeServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();

        System.out.println("init started");

        // TODO - add dependency injection framework, this is seriously janky in 2017!
        new DatastoreInitialiser()
            .init();

        System.out.println("init finished");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getHeader("Accept").contains("json")) {
            Session session = HibernateUtil.getSessionFactory().openSession();
            List<CakeEntity> list = session.createCriteria(CakeEntity.class).list();
            new ObjectMapper().writeValue(resp.getOutputStream(), list);
        } else {
            RequestDispatcher view = req.getRequestDispatcher("/cakes.html");
            view.forward(req, resp);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // De-serialise
        CakeEntity c = new CakeEntity();
        c.setTitle(req.getParameter("title"));
        c.setDescription(req.getParameter("description"));
        c.setImage(req.getParameter("image"));

        // Persist
        // TODO - move this to a Repository layer and the orchestration to a controller
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(c);
        transaction.commit();
        session.close();
    }

}
