package com.waracle.cakemgr.mvc;

import static org.hamcrest.Matchers.equalToCompressingWhiteSpace;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.waracle.cakemgr.dao.CakeRepository;
import com.waracle.cakemgr.dao.DataLoader;
import com.waracle.cakemgr.dao.DataLoaderTest;
import com.waracle.cakemgr.model.CakeEntity;

@WebMvcTest(CakeController.class)
public class CakeControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CakeRepository repo;

	@Test
	public void getById() throws Exception {
		CakeEntity cake = new CakeEntity();
		cake.setId(1);
		cake.setTitle("Lemon cheesecake");
		cake.setDescription("A cheesecake made of lemon");
		cake.setImage("lemoncheesecake_lg.jpg");
		when(repo.findById(1)).thenReturn(Optional.of(cake));
		mockMvc.perform(get("/cakes/1")
				.contentType(MediaType.APPLICATION_JSON))
			//.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().string(equalToCompressingWhiteSpace(
					"{\"title\":\"Lemon cheesecake\",\"desc\":\"A cheesecake made of lemon\",\"image\":\"lemoncheesecake_lg.jpg\",\"id\":1}" )));
	}

	@Test
	public void getById404() throws Exception {
		when(repo.findById(1)).thenReturn(Optional.empty());
		mockMvc.perform(get("/cakes/1")
				.contentType(MediaType.APPLICATION_JSON))
			//.andDo(print())
			.andExpect(status().isNotFound());
	}

	@Test
	public void list() throws Exception {
		String cakeJson = DataLoaderTest.cakesForTesting();
		String expected = cakeJson.replaceAll("\\r\\n?", "\n").substring(0, 599) + "]"; // Data is 20 duplicates of 5 cakes
		List<CakeEntity> cakes = new ArrayList<>(DataLoader.parseCakes(cakeJson));
		when(repo.findAll()).thenReturn(cakes);
		mockMvc.perform(get("/cakes")
				.contentType(MediaType.APPLICATION_JSON))
			//.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().json(expected));
	}

	@Test
	public void create() throws Exception {
		String newCake = "{\"title\":\"Chocolate\",\"desc\":\"Rich dark chocolate\",\"image\":\"darkchoc.jpg\"}";
		String created = "{\"title\":\"Chocolate\",\"desc\":\"Rich dark chocolate\",\"image\":\"darkchoc.jpg\",\"id\":7}";
		when(repo.saveAndFlush(any())).then((invocation) -> {
			CakeEntity create = invocation.getArgument(0);
			Assertions.assertEquals("Chocolate", create.getTitle());
			Assertions.assertEquals("Rich dark chocolate", create.getDescription());
			Assertions.assertEquals("darkchoc.jpg", create.getImage());
			Assertions.assertEquals(null, create.getId());
			create.setId(7);
			return create;
		});
		mockMvc.perform(post("/cakes")
				.contentType(MediaType.APPLICATION_JSON)
				.content(newCake))
			//.andDo(print())
			.andExpect(status().isCreated())
			.andExpect(content().string(equalToCompressingWhiteSpace(created)));
	}

	@Test
	public void update() throws Exception {
		String updateMe = "{\"title\":\"Chocolate\",\"desc\":\"Rich dark chocolate\",\"image\":\"darkchoc.jpg\"}";
		String updated = "{\"title\":\"Chocolate\",\"desc\":\"Rich dark chocolate\",\"image\":\"darkchoc.jpg\",\"id\":293}";
		when(repo.saveAndFlush(any())).then((invocation) -> {
			CakeEntity update = invocation.getArgument(0);
			Assertions.assertEquals("Chocolate", update.getTitle());
			Assertions.assertEquals("Rich dark chocolate", update.getDescription());
			Assertions.assertEquals("darkchoc.jpg", update.getImage());
			Assertions.assertEquals(293, update.getId());
			return update;
		});
		mockMvc.perform(patch("/cakes/293")
				.contentType(MediaType.APPLICATION_JSON)
				.content(updateMe))
			//.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().string(equalToCompressingWhiteSpace(updated)));
	}

	@Test
	public void deleteById() throws Exception {
		mockMvc.perform(delete("/cakes/415")
				.contentType(MediaType.APPLICATION_JSON))
			//.andDo(print())
			.andExpect(status().isNoContent());
		verify(repo).deleteById(415);
	}
}
