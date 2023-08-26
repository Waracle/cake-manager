package com.philldenness.cakemanager.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import com.philldenness.cakemanager.dto.CakeDTO;
import com.philldenness.cakemanager.entity.CakeEntity;
import com.philldenness.cakemanager.mapper.CakeMapper;
import com.philldenness.cakemanager.repository.CakeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CakeServiceTest {

	@InjectMocks
	private CakeService cakeService;

	@Mock
	private CakeRepository cakeRepository;

	@Mock
	private CakeMapper cakeMapper;

	// region all cakes
	@Test
	void shouldCallMapperWithEntity() {
		CakeDTO cakeDTO1 = mock(CakeDTO.class);
		CakeDTO cakeDTO2 = mock(CakeDTO.class);
		CakeEntity cakeEntity1 = mock(CakeEntity.class);
		CakeEntity cakeEntity2 = mock(CakeEntity.class);

		when(cakeRepository.findAll()).thenReturn(List.of(cakeEntity1, cakeEntity2));
		when(cakeMapper.toDTO(any(CakeEntity.class))).thenReturn(cakeDTO1).thenReturn(cakeDTO2);

		List<CakeDTO> cakes = cakeService.getCakes();

		verify(cakeMapper, times(1)).toDTO(cakeEntity1);
		verify(cakeMapper, times(1)).toDTO(cakeEntity2);
		assertEquals(List.of(cakeDTO1, cakeDTO2), cakes);
	}

	// endregion

	// region cake by id
	@Test
	void shouldCallRepoWithId() {
		Long id = 1L;
		when(cakeRepository.findById(anyLong())).thenReturn(Optional.of(mock(CakeEntity.class)));

		cakeService.getCakeById(id);

		verify(cakeRepository).findById(id);
	}

	@Test
	void shouldPassEntityToMapper() {
		Long id = 1L;
		CakeDTO expectedCake = mock(CakeDTO.class);
		CakeEntity cakeEntity = mock(CakeEntity.class);
		when(cakeRepository.findById(anyLong())).thenReturn(Optional.of(cakeEntity));
		when(cakeMapper.toDTO(any(CakeEntity.class))).thenReturn(expectedCake);

		CakeDTO cakeDTO = cakeService.getCakeById(id);

		verify(cakeMapper).toDTO(cakeEntity);
		assertEquals(expectedCake, cakeDTO);
	}

	@Test
	void shouldThrowIllegalArgumentExceptionWhenRepoOptionalIsEmpty() {
		when(cakeRepository.findById(anyLong())).thenReturn(Optional.empty());

		assertThrows(IllegalArgumentException.class, () -> cakeService.getCakeById(9L));
	}

	// endregion
}