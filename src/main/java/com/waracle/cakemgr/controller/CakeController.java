package com.waracle.cakemgr.controller;

import com.waracle.cakemgr.dto.CakeDto;
import com.waracle.cakemgr.service.CakeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cakes")
@Tag(name = "Cake Management", description = "APIs for managing cakes")
public class CakeController {

    @Autowired
    private CakeService cakeService;

    @GetMapping
    @Operation(summary = "Get all cakes", description = "Retrieves a list of all cakes in the system")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of cakes")
    public ResponseEntity<List<CakeDto>> getAllCakes() {
        List<CakeDto> cakes = cakeService.getAllCakes();
        return ResponseEntity.ok(cakes);
    }

    @PostMapping
    @Operation(summary = "Create a new cake", description = "Creates a new cake entry in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cake created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<CakeDto> createCake(@Valid @RequestBody CakeDto cakeDto) {
        CakeDto savedCake = cakeService.createCake(cakeDto);
        return ResponseEntity.ok(savedCake);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a cake", description = "Updates an existing cake by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cake updated successfully"),
            @ApiResponse(responseCode = "404", description = "Cake not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<CakeDto> updateCake(
            @Parameter(description = "ID of the cake to update") @PathVariable Long id,
            @Valid @RequestBody CakeDto cakeDto) {
        CakeDto updatedCake = cakeService.updateCake(id, cakeDto);
        return ResponseEntity.ok(updatedCake);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a cake", description = "Deletes a cake by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cake deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Cake not found")
    })
    public ResponseEntity<Void> deleteCake(
            @Parameter(description = "ID of the cake to delete") @PathVariable Long id) {
        cakeService.deleteCake(id);
        return ResponseEntity.noContent().build();
    }

}
