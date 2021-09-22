package com.waracle.cakemgr;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.util.List;

@WebServlet("/cakes")
public class CakeServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();

        System.out.println("init started");


        System.out.println("downloading cake json");
        try (InputStream inputStream = new URL("https://gist.githubusercontent.com/hart88/198f29ec5114a3ec3460/raw/8dd19a88f9b8d24c23d9960f3300d0c917a4f07c/cake.json").openStream()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuffer buffer = new StringBuffer();
            String line = reader.readLine();
            while (line != null) {
                buffer.append(line);
                line = reader.readLine();
            }

            System.out.println("parsing cake json");
            JsonParser parser = new JsonFactory().createParser(buffer.toString());
            if (JsonToken.START_ARRAY != parser.nextToken()) {
                throw new Exception("bad token");
            }

            JsonToken nextToken = parser.nextToken();
            while(nextToken == JsonToken.START_OBJECT) {
                System.out.println("creating cake entity");

                CakeEntity cakeEntity = new CakeEntity();
                System.out.println(parser.nextFieldName());
                cakeEntity.setTitle(parser.nextTextValue());

                System.out.println(parser.nextFieldName());
                cakeEntity.setDescription(parser.nextTextValue());

                System.out.println(parser.nextFieldName());
                cakeEntity.setImage(parser.nextTextValue());

                Session session = HibernateUtil.getSessionFactory().openSession();
                try {
                    session.beginTransaction();
                    session.persist(cakeEntity);
                    System.out.println("adding cake entity");
                    session.getTransaction().commit();
                } catch (ConstraintViolationException ex) {

                }
                session.close();

                nextToken = parser.nextToken();
                System.out.println(nextToken);

                nextToken = parser.nextToken();
                System.out.println(nextToken);
            }

        } catch (Exception ex) {
            throw new ServletException(ex);
        }

        System.out.println("init finished");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Session session = HibernateUtil.getSessionFactory().openSession();
        List<CakeEntity> list = session.createCriteria(CakeEntity.class).list();

        resp.getWriter().println("[");

        for (CakeEntity entity : list) {
            resp.getWriter().println("\t{");

            resp.getWriter().println("\t\t\"title\" : " + entity.getTitle() + ", ");
            resp.getWriter().println("\t\t\"desc\" : " + entity.getDescription() + ",");
            resp.getWriter().println("\t\t\"image\" : " + entity.getImage());

            resp.getWriter().println("\t}");
        }

        resp.getWriter().println("]");

    }

}
