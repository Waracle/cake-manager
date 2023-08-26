package com.philldenness.cakemanager.controller;

import java.util.List;

import com.philldenness.cakemanager.dto.CakeDTO;
import com.philldenness.cakemanager.service.CakeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cakes")
@RequiredArgsConstructor
public class CakeController {

    private final CakeService cakeService;

    @GetMapping
    @Operation(summary = "Get all cakes")
    public List<CakeDTO> getAllCakes() {
        return cakeService.getCakes();
    }

	@GetMapping("/{id}")
	@Operation(summary = "Get cake by its ID")
	public CakeDTO getCakeById(@PathVariable Long id) {
		return cakeService.getCakeById(id);
	}
}
