package com.philldenness.cakemanager.integrationTest;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
public class GetCakeIT {

	@Autowired
	private MockMvc mvc;

	// region get all
	@Test
	void testGetCakes_returnsAllCakes() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/api/cakes")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(20));
	}
	// endregion

	// region get by id
	@Test
	void testGetCakeById_returnsCake() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/api/cakes/1")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().json("{'title': 'Lemon cheesecake', 'description':  'A cheesecake made of lemon', 'image': 'https://s3-eu-west-1.amazonaws.com/s3.mediafileserver.co.uk/carnation/WebFiles/RecipeImages/lemoncheesecake_lg.jpg'}"));
	}

	@Test
	void testGetCakeById_returns404WhenIdDoesntExist() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/api/cakes/99")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	@Test
	void testGetCakeById_returns400WhenIdIsNotALong() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/api/cakes/abc")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}
	// endregion
}
