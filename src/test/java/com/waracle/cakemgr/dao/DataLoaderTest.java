package com.waracle.cakemgr.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import com.waracle.cakemgr.model.CakeEntity;

@DataJpaTest
@Import(DataLoader.class)
public class DataLoaderTest {

	@Autowired
	private CakeRepository repo;

	@Autowired
	private DataLoader loader;

	@Test
	public void loadCakesDeDupe() throws IOException {
		loader.loadCakes(cakesForTesting());
        assertEquals( 5, repo.count() );
        assertEquals( "Lemon cheesecake, victoria sponge, Carrot cake, Banana cake, Birthday cake", repo.findAll().stream()
        	.map(CakeEntity::getTitle)
        	.collect(Collectors.joining(", ")));
	}

	public static String cakesForTesting() throws IOException {
        try(InputStream in = DataLoaderTest.class.getResourceAsStream("cake.json")) {
        	return new String(in.readAllBytes(), StandardCharsets.UTF_8);
        }
	}
}
