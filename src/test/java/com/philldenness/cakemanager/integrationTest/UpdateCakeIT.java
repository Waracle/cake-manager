package com.philldenness.cakemanager.integrationTest;

import static com.philldenness.cakemanager.testUtils.TestUtils.asJsonString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.philldenness.cakemanager.dto.CakeRequest;
import com.philldenness.cakemanager.entity.CakeEntity;
import com.philldenness.cakemanager.repository.CakeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
public class UpdateCakeIT {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private CakeRepository cakeRepository;

	@Test
	void testValidUpdateCake() throws Exception {
		CakeEntity existingEntity = new CakeEntity();
		existingEntity.setTitle("old title");
		existingEntity.setDescription("old desc");
		existingEntity.setImage("old image");
		existingEntity = cakeRepository.save(existingEntity);

		CakeRequest request = new CakeRequest("new title", "new description", "new image");

		mvc.perform(MockMvcRequestBuilders.put("/cakes/" + existingEntity.getId())
						.content(asJsonString(request))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().json("{title: 'new title', description: 'new description', image: 'new image'}"));

		CakeEntity updatedEntity = cakeRepository.findById(existingEntity.getId()).orElseThrow();
		assertEquals(request.title(), updatedEntity.getTitle());
		assertEquals(request.description(), updatedEntity.getDescription());
		assertEquals(request.image(), updatedEntity.getImage());
	}

	@Test
	void testUpdateCakeReturns404WhenIdDoesntExist() throws Exception {
		mvc.perform(MockMvcRequestBuilders.put("/cakes/99")
						.content(asJsonString(new CakeRequest("new title", "new description", "new image")))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	@Test
	void testInvalidPostCreatesCakeWithNullTitle() throws Exception {
		mvc.perform(MockMvcRequestBuilders.put("/cakes/1")
						.content(asJsonString(new CakeRequest(null, "new description", "new image")))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	@Test
	void testInvalidPostCreatesCakeWithNullDescription() throws Exception {
		mvc.perform(MockMvcRequestBuilders.put("/cakes/1")
						.content(asJsonString(new CakeRequest("new title", null, "new image")))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	@Test
	void testInvalidPostCreatesCakeWithNullImage() throws Exception {
		mvc.perform(MockMvcRequestBuilders.put("/cakes/1")
						.content(asJsonString(new CakeRequest("new title", "new description", null)))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}
}
