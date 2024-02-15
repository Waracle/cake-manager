package com.waracle.cakemgr.mvc;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.waracle.cakemgr.dao.CakeEntity;

//@WebServlet("/cakes")
public class CakeServlet {

    public void init() {

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

                /*
                Session session = HibernateUtil.getSessionFactory().openSession();
                try {
                    session.beginTransaction();
                    session.persist(cakeEntity);
                    System.out.println("adding cake entity");
                    session.getTransaction().commit();
                } catch (ConstraintViolationException ex) {

                }
                session.close();
                */

                nextToken = parser.nextToken();
                System.out.println(nextToken);

                nextToken = parser.nextToken();
                System.out.println(nextToken);
            }

        } catch (Exception ex) {
            //throw new ServletException(ex);
        }

        System.out.println("init finished");
    }

    public void doGet(/*HttpServletRequest req, HttpServletResponse resp*/) {

        //Session session = HibernateUtil.getSessionFactory().openSession();
        List<CakeEntity> list = new ArrayList<>(); // session.createCriteria(CakeEntity.class).list();
        PrintWriter writer = new PrintWriter(new StringWriter());

        writer.println("[");

        for (CakeEntity entity : list) {
            writer.println("\t{");

            writer.println("\t\t\"title\" : " + entity.getTitle() + ", ");
            writer.println("\t\t\"desc\" : " + entity.getDescription() + ",");
            writer.println("\t\t\"image\" : " + entity.getImage());

            writer.println("\t}");
        }

        writer.println("]");
    }
}
