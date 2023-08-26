package com.philldenness.cakemanager.integrationTest;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.philldenness.cakemanager.dto.CakeRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
public class CreateCakeIT {

	@Autowired
	private MockMvc mvc;

	@Test
	void testValidPostCreatesCake() throws Exception {
		mvc.perform(MockMvcRequestBuilders.post("/cakes")
						.content(asJsonString(new CakeRequest("new title", "new description", "new image")))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(content().json("{id: 21, title: 'new title', description: 'new description', image: 'new image'}"));
	}

	@Test
	void testInvalidPostCreatesCakeWithNullTitle() throws Exception {
		mvc.perform(MockMvcRequestBuilders.post("/cakes")
						.content(asJsonString(new CakeRequest(null, "new description", "new image")))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	@Test
	void testInvalidPostCreatesCakeWithNullDescription() throws Exception {
		mvc.perform(MockMvcRequestBuilders.post("/cakes")
						.content(asJsonString(new CakeRequest("new title", null, "new image")))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	@Test
	void testInvalidPostCreatesCakeWithNullImage() throws Exception {
		mvc.perform(MockMvcRequestBuilders.post("/cakes")
						.content(asJsonString(new CakeRequest("new title", "new description", null)))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
