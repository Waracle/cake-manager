package com.waracle.cakemgr.dao;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.waracle.cakemgr.model.CakeEntity;

import jakarta.annotation.PostConstruct;

@Component
public class DataLoader {

	private static final ObjectMapper mapper = new ObjectMapper();

	private final CakeRepository repo;
	
	@Value("${cake.json.url}")
	private String cakeJsonUrl;

	@Value("${load.cakes.onstartup}")
	private boolean loadCakesOnstartup;

	@Autowired
	public DataLoader(CakeRepository repo) {
		this.repo = repo;
	}

	@PostConstruct
    public void init() throws IOException {
		if(loadCakesOnstartup) {
	        RestClient client = RestClient.create();
	        ResponseEntity<String> result = client.get() 
	        		  .uri(cakeJsonUrl)
	        		  .accept(MediaType.APPLICATION_JSON)
	        		  .retrieve()
	        		  .toEntity(String.class);
	        loadCakes(result.getBody());
		}
	}

    void loadCakes(String cakes) throws IOException {
    	repo.saveAllAndFlush( parseCakes(cakes) );
    }

    public static Collection<CakeEntity> parseCakes(String cakes) throws JsonMappingException, JsonProcessingException {
    	List<CakeEntity> list = mapper.readerForListOf(CakeEntity.class).readValue(cakes);
    	Collection<CakeEntity> set = list.stream()
    		.collect(Collectors.toMap(CakeEntity::getTitle,
    				Function.identity(),
    				(cake1, cake2) -> cake1, // de-dupe cake by title
    				LinkedHashMap::new)) // preserve iteration sequence
    		.values();
    	return set;
    }
}
