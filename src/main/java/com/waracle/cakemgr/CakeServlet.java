package com.waracle.cakemgr;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@WebServlet(urlPatterns = {"/", "/cakes"},
        loadOnStartup = 1)
public class CakeServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(CakeServlet.class);
    private static final String APPLICATION_JSON = "application/json";
    private static final String ACCEPT = "accept";
    private static final String INPUT_JSON = "src/main/resources/initial-cakes.json";
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private final CakeFactory cakeFactory;
    private final CakeStore cakeStore;

    public CakeServlet() {
        this(new CakeFactory(), new CakeStore());
    }

    CakeServlet(CakeFactory cakeFactory, CakeStore cakeStore) {
        super();
        this.cakeFactory = cakeFactory;
        this.cakeStore = cakeStore;
    }

    @Override
    public void init() throws ServletException {
        super.init();

        LOGGER.debug("init started");

        try {
            fillStore();
        } catch (IOException ex) {
            LOGGER.error("error loading initial cake collection", ex);
            throw new ServletException(ex);
        }

        LOGGER.info("init finished");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.debug("GET /cakes");
        if (req.getHeader(ACCEPT).contains(APPLICATION_JSON)) {
            resp.getWriter().print(cakesAsJson());
        } else {
            req.setAttribute("cakes", cakeStore.allCakes());
            getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.debug("POST /cakes");
        cakeStore.store(cakeFactory.makeCake(req));
        doGet(req, resp);
    }

    @Override
    public void destroy() {
        super.destroy();
        HibernateUtil.shutdown();
    }

    private String cakesAsJson() throws JsonProcessingException {
        return MAPPER.writeValueAsString(cakeStore.allCakes());
    }


    private void fillStore() throws IOException {
        initialCakes().stream().forEach(cake -> cakeStore.store(cake));
    }

    private List<Cake> initialCakes() throws IOException {
        try (InputStream inputStream = new FileInputStream(INPUT_JSON)) {
            return MAPPER.readValue(inputStream, new TypeReference<List<Cake>>(){});
        }
    }

}
