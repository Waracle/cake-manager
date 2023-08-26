package com.philldenness.cakemanager.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
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

	// region allCakes
	@Test
	void shouldReturnAllCakesFromCakeService() {
		List<CakeDTO> cakes = List.of(new CakeDTO("title", "description", "image"));
		when(cakeService.getCakes()).thenReturn(cakes);

		List<CakeDTO> cakeList = cakeController.getAllCakes();

		assertEquals(cakes, cakeList);
	}
	// endregion

	// region cake by id
	@Test
	void shouldCallServiceWithPathIdAndReturnCakeDto() {
		Long cakeId = 1L;
		CakeDTO expectedCake = new CakeDTO("title", "description", "image");
		when(cakeService.getCakeById(cakeId)).thenReturn(expectedCake);

		CakeDTO cakeDTO = cakeController.getCakeById(cakeId);

		assertEquals(expectedCake, cakeDTO);
	}

	@Test
	void shouldThrowIllegalArgumentExceptionWhenServiceThrowsIllegalArgumentException() {
		when(cakeService.getCakeById(anyLong())).thenThrow(IllegalArgumentException.class);

		assertThrows(IllegalArgumentException.class, () -> cakeController.getCakeById(9L));
	}

	// endregion
}