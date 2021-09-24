package com.waracle.cakes.route;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.waracle.cakes.model.dto.CakeDTO;
import com.waracle.cakes.service.CakeService;

@Component
public class CakesRoute extends RouteBuilder {

	@Autowired
	private Environment env;

	@SuppressWarnings("unchecked")
	@Override
	public void configure() throws Exception {
		
		onException(JsonParseException.class, UnrecognizedPropertyException.class, 
				IllegalArgumentException.class, DataIntegrityViolationException.class)
			.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(400))
			.setBody(constant("400 Bad Request"))
			.handled(true)
			.log(LoggingLevel.ERROR, "${exchangeProperty[CamelExceptionCaught]}")
			.end();
		onException(Exception.class)
			.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(500))
			.setBody(constant("500 Server error"))
			.handled(true)
			.log(LoggingLevel.ERROR, "${exchangeProperty[CamelExceptionCaught]}")
			.end();
		

		restConfiguration().component("servlet").contextPath("/").apiContextPath("/api-doc")
				.apiProperty("api.title", "Cakes REST API")
				.apiProperty("api.version", "1.0")
				.apiProperty("cors", "true")
				.apiContextRouteId("doc-api")
				.port(env.getProperty("server.port", "8080"))
				.bindingMode(RestBindingMode.json)
				.dataFormatProperty("prettyPrint", "true");

		
		rest().description("Cakes REST service")
			.get("").produces("text/html")
				.route()
					.bean(CakeService.class, "findCakes")
					.to("velocity:cakes-view.vm")
					.log("direct:velocityView logging")
				.endRest()
			.get("/cakes").outType(CakeDTO[].class)
				.route().routeId("list-cakes-api")
					.bean(CakeService.class, "findCakes")
				.endRest()
			.post("/cakes").type(CakeDTO.class)
				.route().routeId("create-cake-api")
					.bean(CakeService.class, "createCake(${body})")
					.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(201));
			
	}

}
