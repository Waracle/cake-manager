package com.philldenness.cakemanager.integrationTest;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.philldenness.cakemanager.entity.CakeEntity;
import com.philldenness.cakemanager.repository.CakeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
public class GetCakeIT {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private CakeRepository cakeRepository;

	@BeforeEach
	void setUp() {
		CakeEntity cakeEntity = new CakeEntity();
		cakeEntity.setEmployeeId(1L);
		cakeEntity.setTitle("Lemon cheesecake");
		cakeEntity.setDescription("A cheesecake made of lemon");
		cakeEntity.setImage("https://s3-eu-west-1.amazonaws.com/s3.mediafileserver.co.uk/carnation/WebFiles/RecipeImages/lemoncheesecake_lg.jpg");

		cakeRepository.save(cakeEntity);
	}

	@Test
	void testGetCakesReturnsAllCakes() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/cakes").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().json("[{'title':'Lemon cheesecake','description':'A cheesecake made of lemon','image':'https://s3-eu-west-1.amazonaws.com/s3.mediafileserver.co.uk/carnation/WebFiles/RecipeImages/lemoncheesecake_lg.jpg'}]"));

	}
}
