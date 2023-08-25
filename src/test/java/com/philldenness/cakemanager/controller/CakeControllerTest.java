package com.philldenness.cakemanager.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;

import com.philldenness.cakemanager.dto.CakeDTO;
import com.philldenness.cakemanager.service.CakeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CakeControllerTest {

	@Mock
	private CakeService cakeService;

	@InjectMocks
	private CakeController cakeController;

	@Test
	void shouldGetCakeReturnsCakesFromCakeService() {
		List<CakeDTO> cakes = List.of(new CakeDTO("title", "description", "image"));
		when(cakeService.getCakes()).thenReturn(cakes);

		List<CakeDTO> cakeList = cakeController.getAllCakes();

		assertEquals(cakes, cakeList);
	}
}