package com.waracle.cakemgr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

class CakeFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(CakeFactory.class);
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static final String IMAGE = "image";

    Cake makeCake(final HttpServletRequest req) throws IOException {
        Cake cake = new Cake();
        String title = req.getParameter(TITLE);
        LOGGER.debug("title: {}", title);
        cake.setTitle(title);
        String description = req.getParameter(DESCRIPTION);
        LOGGER.debug("description: {}", description);
        cake.setDescription(description);
        String image = req.getParameter(IMAGE);
        LOGGER.debug("image: {}", image);
        cake.setImage(image);
        return cake;
    }
}
