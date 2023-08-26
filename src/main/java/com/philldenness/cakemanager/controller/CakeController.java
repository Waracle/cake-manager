package com.philldenness.cakemanager.controller;

import java.util.List;

import com.philldenness.cakemanager.dto.CakeDTO;
import com.philldenness.cakemanager.dto.CakeRequest;
import com.philldenness.cakemanager.service.CakeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
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
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Found the cake",
					content = {@Content(mediaType = "application/json",
							schema = @Schema(implementation = CakeDTO.class))}),
			@ApiResponse(responseCode = "400", description = "Invalid id supplied",
					content = @Content),
			@ApiResponse(responseCode = "404", description = "Cake not found",
					content = @Content)})
	public CakeDTO getCakeById(@PathVariable Long id) {
		return cakeService.getCakeById(id);
	}

	@PostMapping
	@Operation(summary = "Create a cake")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Created the cake",
					content = {@Content(mediaType = "application/json",
							schema = @Schema(implementation = CakeDTO.class))}),
			@ApiResponse(responseCode = "400", description = "Invalid payload supplied",
					content = @Content)
	})
	@ResponseStatus(HttpStatus.CREATED)
	public CakeDTO createCake(@Valid @RequestBody CakeRequest payloadCake) {
		return cakeService.create(payloadCake);
	}

	@PutMapping("/{id}")
	@Operation(summary = "Update cake")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Updated the cake",
					content = {@Content(mediaType = "application/json",
							schema = @Schema(implementation = CakeDTO.class))}),
			@ApiResponse(responseCode = "400", description = "Invalid payload supplied",
					content = @Content),
			@ApiResponse(responseCode = "404", description = "Cake not found",
					content = @Content)})
	public CakeDTO updateCake(@PathVariable Long id, @Valid @RequestBody CakeRequest payloadCake) {
		return cakeService.update(id, payloadCake);
	}
}
