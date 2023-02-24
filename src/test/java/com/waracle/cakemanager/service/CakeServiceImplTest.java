package com.waracle.cakemanager.service;

import com.waracle.cakemanager.entity.Cake;
import com.waracle.cakemanager.exceptions.NotFoundException;
import com.waracle.cakemanager.repository.CakeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CakeServiceImplTest {

    @Mock
    private CakeRepository cakeRepository;

    @InjectMocks
    private CakeService cakeService = new CakeServiceImpl();

    @Test
    public void getAllCakes_shouldReturnAllCakes() {
        List<Cake> expectedCakes = new ArrayList<>();
        expectedCakes.add(new Cake(1L, "Chocolate Cake", "A delicious chocolate cake", "https://example.com/chocolate-cake.jpg"));
        expectedCakes.add(new Cake(2L, "Carrot Cake", "A delicious carrot cake", "https://example.com/carrot-cake.jpg"));

        when(cakeRepository.findAll()).thenReturn(expectedCakes);

        List<Cake> actualCakes = cakeService.getAllCakes();

        assertEquals(expectedCakes.size(), actualCakes.size());
        for (int i = 0; i < expectedCakes.size(); i++) {
            assertEquals(expectedCakes.get(i), actualCakes.get(i));
        }
    }

    @Test
    public void getCakeById_shouldReturnCake() {
        Cake expectedCake = getTestCake();

        when(cakeRepository.findById(expectedCake.getId())).thenReturn(Optional.of(expectedCake));

        Cake actualCake = cakeService.getCakeById(expectedCake.getId());

        assertEquals(expectedCake, actualCake);
    }

    @Test
    public void getCakeById_shouldThrowNotFoundException_forNonExistentId() {
        long nonExistentId = 123;

        when(cakeRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> cakeService.getCakeById(nonExistentId));
    }


    @Test
    public void shouldAddCake() {
        Cake cakeToAdd = new Cake(null, "Chocolate Cake", "A delicious chocolate cake", "https://example.com/chocolate-cake.jpg");
        Cake expectedCake = new Cake(1L, "Chocolate Cake", "A delicious chocolate cake", "https://example.com/chocolate-cake.jpg");

        when(cakeRepository.save(any(Cake.class))).thenReturn(expectedCake);

        Cake actualCake = cakeService.addCake(cakeToAdd);

        assertEquals(expectedCake, actualCake);
    }

    @Test
    public void shouldUpdateCake() {
        Cake cakeToUpdate = getTestCake();
        Cake expectedCake = getTestCake();

        when(cakeRepository.findById(cakeToUpdate.getId())).thenReturn(Optional.of(cakeToUpdate));
        when(cakeRepository.save(any(Cake.class))).thenReturn(expectedCake);

        Cake actualCake = cakeService.updateCake(cakeToUpdate.getId(), expectedCake);

        assertEquals(expectedCake, actualCake);
    }

    @Test
    public void updateCake_shouldThrowNotFoundException_forNonExistentId() {
        long nonExistentId = 123;
        Cake cakeToUpdate = new Cake(nonExistentId, "Chocolate Cake", "A delicious chocolate cake", "https://example.com/chocolate-cake.jpg");

        when(cakeRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> cakeService.updateCake(nonExistentId, cakeToUpdate));
    }

    @Test
    public void shouldDeleteCake() {
        Cake cakeToDelete = getTestCake();

        when(cakeRepository.findById(cakeToDelete.getId())).thenReturn(Optional.of(cakeToDelete));
        assertDoesNotThrow(() -> cakeService.deleteCake(cakeToDelete.getId()));
        verify(cakeRepository, times(1)).delete(cakeToDelete);
    }

    @Test
    public void deleteCake_shouldThrowNotFoundException_forNonExistentId() {
        long nonExistentId = 123;
        when(cakeRepository.findById(nonExistentId)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> cakeService.deleteCake(nonExistentId));
    }

    private Cake getTestCake() {
        return new Cake(1L, "Chocolate Cake", "A delicious chocolate cake", "https://example.com/chocolate-cake.jpg");
    }
}