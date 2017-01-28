package com.waracle.cakemgr;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.Session;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;

import static com.waracle.cakemgr.MinimalHttpClient.httpGet;

public class DatastoreInitialiser {
    public void init() throws ServletException {
        try {
            System.out.println("downloading cake json");
            List<CakeEntity> cakes = downloadCakes();

            System.out.println("persisting initial cakes");
            persistCakes(cakes);
        } catch (IOException ex) {
            throw new ServletException("Failed to init data store", ex);
        }
    }

    private void persistCakes(List<CakeEntity> cakes) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        for (CakeEntity cake : cakes) {
            System.out.println("adding cake entity");
            session.beginTransaction();
            session.persist(cake);
            System.out.println("committing cake");
            session.getTransaction().commit();
        }

        session.close();
    }

    private List<CakeEntity> downloadCakes() throws IOException {
        ObjectMapper om = new ObjectMapper();
        om.addMixIn(CakeEntity.class, CakeEntityMixIn.class);
        return om.readValue(httpGet("https://gist.githubusercontent.com/hart88/198f29ec5114a3ec3460/raw/8dd19a88f9b8d24c23d9960f3300d0c917a4f07c/cake.json", "application/json"), new TypeReference<List<CakeEntity>>(){});
    }

    private static class CakeEntityMixIn {
        public String title;
        @JsonProperty("desc")
        public String description;
        public String image;
    }
}
