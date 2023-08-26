package com.philldenness.cakemanager.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import java.util.List;

import com.philldenness.cakemanager.dto.CakeDTO;
import com.philldenness.cakemanager.dto.CakeRequest;
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
		List<CakeDTO> cakes = List.of(mock(CakeDTO.class));
		when(cakeService.getCakes()).thenReturn(cakes);

		List<CakeDTO> cakeList = cakeController.getAllCakes();

		assertEquals(cakes, cakeList);
	}
	// endregion

	// region cake by id
	@Test
	void shouldCallServiceWithPathIdAndReturnCakeDto() {
		Long cakeId = 1L;
		CakeDTO expectedCake = mock(CakeDTO.class);
		when(cakeService.getCakeById(cakeId)).thenReturn(expectedCake);

		CakeDTO cakeDTO = cakeController.getCakeById(cakeId);

		assertEquals(expectedCake, cakeDTO);
	}

	@Test
	void shouldThrowIllegalArgumentExceptionWhenGetByIdThrowsIllegalArgumentException() {
		when(cakeService.getCakeById(anyLong())).thenThrow(IllegalArgumentException.class);

		assertThrows(IllegalArgumentException.class, () -> cakeController.getCakeById(9L));
	}

	// endregion

	// region create cake
	@Test
	void shouldCallCreateWithCakePayload() {
		CakeDTO expectedCake = mock(CakeDTO.class);
		CakeRequest payloadCake = mock(CakeRequest.class);
		when(cakeService.create(any(CakeRequest.class))).thenReturn(expectedCake);

		CakeDTO createdCake = cakeController.createCake(payloadCake);

		verify(cakeService).create(payloadCake);
		assertEquals(expectedCake, createdCake);
	}

	@Test
	void shouldThrowIllegalArgumentExceptionWhenCreateThrowsIllegalArgumentException() {
		when(cakeService.create(any(CakeRequest.class))).thenThrow(IllegalArgumentException.class);

		assertThrows(IllegalArgumentException.class, () -> cakeController.createCake(mock(CakeRequest.class)));
	}
	// endregion

	// region update cake
	@Test
	void shouldCallUpdateWithCakePayload() {
		Long id = 1L;
		CakeDTO expectedCake = mock(CakeDTO.class);
		CakeRequest payloadCake = mock(CakeRequest.class);
		when(cakeService.update(anyLong(), any(CakeRequest.class))).thenReturn(expectedCake);

		CakeDTO updatedCake = cakeController.updateCake(id, payloadCake);

		verify(cakeService).update(id, payloadCake);
		assertEquals(expectedCake, updatedCake);
	}

	@Test
	void shouldThrowIllegalArgumentExceptionWhenUpdateThrowsIllegalArgumentException() {
		when(cakeService.update(anyLong(), any(CakeRequest.class))).thenThrow(IllegalArgumentException.class);

		assertThrows(IllegalArgumentException.class, () -> cakeController.updateCake(1L, mock(CakeRequest.class)));
	}
	// endregion

	// region delete cake
	@Test
	void shouldCallDeleteWithId() {
		Long id = 1L;
		cakeController.deleteCake(id);

		verify(cakeService).delete(id);
	}

	@Test
	void shouldThrowIllegalArgumentExceptionWhenDeleteThrowsIllegalArgumentException() {
		doThrow(IllegalArgumentException.class).when(cakeService).delete(anyLong());

		assertThrows(IllegalArgumentException.class, () -> cakeController.deleteCake(1L));
	}
	// endregion
}