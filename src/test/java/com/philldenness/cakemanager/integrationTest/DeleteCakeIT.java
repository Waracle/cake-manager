package com.philldenness.cakemanager.integrationTest;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
public class DeleteCakeIT {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private CakeRepository cakeRepository;

	@Test
	void testDeleteCake() throws Exception {
		CakeEntity cakeEntity = new CakeEntity();
		cakeEntity.setImage("to delete");
		cakeEntity.setTitle("to delete");
		cakeEntity.setDescription("to delete");
		CakeEntity deleteEntity = cakeRepository.save(cakeEntity);

		mvc.perform(MockMvcRequestBuilders.delete("/cakes/" + deleteEntity.getId())
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());
	}

	@Test
	void testDeleteNonExistentCake() throws Exception {
		mvc.perform(MockMvcRequestBuilders.delete("/cakes/99")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}
}
