package com.waracle.cakemgr.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.util.stream.Collectors;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.waracle.cakemgr.model.CakeEntity;

@SpringBootTest(properties = { "load.cakes.onstartup=true", "cake.json.url=http://localhost:8090" })
public class DataLoaderIT {

	@Autowired
	private CakeRepository repo;

	@Autowired
	private DataLoader loader;

	private static Server server = new Server(8090);

    @BeforeAll
    public static void setUp() throws Exception {
        URI uri = DataLoaderIT.class.getResource("cake.json").toURI();
        ResourceHandler handler = new ResourceHandler();
        handler.setBaseResourceAsString(uri.toASCIIString());
        server.setStopAtShutdown(true);
        server.setHandler(handler);
        server.start();
    }
    		

    @AfterAll
    public static void tearDown() throws Exception {
        server.stop();
	}

	@Test
	public void fetchCakeDataHttp() throws Exception {
        assertEquals( 5, repo.count() );
        assertEquals( "Lemon cheesecake, victoria sponge, Carrot cake, Banana cake, Birthday cake", repo.findAll().stream()
        	.map(CakeEntity::getTitle)
        	.collect(Collectors.joining(", ")));
	}
}
