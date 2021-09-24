package com.waracle.cakes.model;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.waracle.cakes.model.dto.CakeDTO;
import com.waracle.cakes.model.entity.Cake;

public class ModelMappingTest {

	
	
	@Test
	public void modelMappingTest() throws JsonParseException, JsonMappingException, IOException {
		
		
		ClassLoader classLoader = getClass().getClassLoader();
	    File file = new File(classLoader.getResource("cakes.json").getFile());
		ObjectMapper objectMapper = new ObjectMapper();

		List<CakeDTO> cakeDtoList = objectMapper.readValue(file, new TypeReference<List<CakeDTO>>(){});
		
		ModelMapper mapper = new ModelMapper();
		
		
		List<Cake> cakesList = 
				StreamSupport.stream(cakeDtoList.spliterator(), false)
				  .map(cake -> mapper.map(cake, Cake.class))
				  .collect(Collectors.toList());
		
		assertEquals(cakeDtoList.size(), cakesList.size());
		
		cakeDtoList = 
				StreamSupport.stream(cakesList.spliterator(), false)
				  .map(cake -> mapper.map(cake, CakeDTO.class))
				  .collect(Collectors.toList());
		
		assertEquals(cakeDtoList.size(), cakesList.size());

		
		
	}
}
