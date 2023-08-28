package com.philldenness.cakemanager.integrationTest;

import static com.philldenness.cakemanager.testUtils.TestUtils.asJsonString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityIT {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private CakeRepository cakeRepository;

	@Test
	void testHealthWithUser_isOk() throws Exception {
		mvc.perform(get("/actuator/health")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	void testInfoWithUser_isOk() throws Exception {
		mvc.perform(get("/actuator/info")
				.with(httpBasic("user", "userPass"))
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	void testInfoWithUser_isUnauthorised() throws Exception {
		mvc.perform(get("/actuator/info")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isUnauthorized());
	}

	@Test
	void testCreateCakeWithUser_returnsCreated() throws Exception {
		mvc.perform(post("/api/cakes")
						.with(httpBasic("user", "userPass"))
						.content(asJsonString(new CakeRequest("new title", "new description", "new image")))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
	}

	@Test
	void testCreateCakeWithoutUser_returnsUnauthorised() throws Exception {
		mvc.perform(post("/api/cakes")
						.content(asJsonString(new CakeRequest("new title", "new description", "new image")))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized());
	}

	@Test
	void testDeleteCakeWithUser_returnsNoContent() throws Exception {
		CakeEntity cakeEntity = new CakeEntity();
		cakeEntity.setImage("to delete");
		cakeEntity.setTitle("to delete");
		cakeEntity.setDescription("to delete");
		CakeEntity deleteEntity = cakeRepository.save(cakeEntity);

		mvc.perform(delete("/api/cakes/" + deleteEntity.getId())
						.with(httpBasic("user", "userPass"))
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());
	}

	@Test
	void testDeleteCakeWithoutUser_returnsUnauthorised() throws Exception {
		CakeEntity cakeEntity = new CakeEntity();
		cakeEntity.setImage("to delete");
		cakeEntity.setTitle("to delete");
		cakeEntity.setDescription("to delete");
		CakeEntity deleteEntity = cakeRepository.save(cakeEntity);

		mvc.perform(delete("/api/cakes/" + deleteEntity.getId())
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized());
	}

	@Test
	void testGetCakeByIdWithUser_returnsOk() throws Exception {
		mvc.perform(get("/api/cakes/1")
				.with(httpBasic("user", "userPass"))
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	void testGetCakeByIdWithoutUser_returnsUnauthorised() throws Exception {
		mvc.perform(get("/api/cakes/1")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isUnauthorized());
	}

	@Test
	void testGetCakesWithUser_returnsOk() throws Exception {
		mvc.perform(get("/api/cakes")
				.with(httpBasic("user", "userPass"))
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	void testGetCakesWithoutUser_returnsUnauthorised() throws Exception {
		mvc.perform(get("/api/cakes")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isUnauthorized());
	}

	@Test
	void testUpdateCakeWithUser_returnsOk() throws Exception {
		CakeEntity existingEntity = new CakeEntity();
		existingEntity.setTitle("old title");
		existingEntity.setDescription("old desc");
		existingEntity.setImage("old image");
		existingEntity = cakeRepository.save(existingEntity);

		CakeRequest request = new CakeRequest("new title", "new description", "new image");

		mvc.perform(put("/api/cakes/" + existingEntity.getId())
						.with(httpBasic("user", "userPass"))
						.content(asJsonString(request))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	void testUpdateCakeWithoutUser_returnsUnauthorised() throws Exception {
		CakeEntity existingEntity = new CakeEntity();
		existingEntity.setTitle("old title");
		existingEntity.setDescription("old desc");
		existingEntity.setImage("old image");
		existingEntity = cakeRepository.save(existingEntity);

		CakeRequest request = new CakeRequest("new title", "new description", "new image");

		mvc.perform(put("/api/cakes/" + existingEntity.getId())
						.content(asJsonString(request))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized());
	}
}
