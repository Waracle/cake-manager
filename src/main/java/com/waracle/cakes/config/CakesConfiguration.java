package com.waracle.cakes.config;

import org.apache.camel.component.jackson.JacksonDataFormat;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.waracle.cakes.model.dto.CakeDTO;

@Configuration
public class CakesConfiguration {
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	@Bean
	public JacksonDataFormat cakeDtoDataFormat() {
		JacksonDataFormat jsonFormat =  new JacksonDataFormat(CakeDTO.class);
		jsonFormat.setPrettyPrint(true);
		return jsonFormat;
	}

}
