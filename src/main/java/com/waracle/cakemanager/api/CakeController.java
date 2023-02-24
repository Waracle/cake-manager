package com.waracle.cakemanager.api;

import com.waracle.cakemanager.entity.Cake;
import com.waracle.cakemanager.service.CakeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cakes")
@Slf4j
public class CakeController {

    @Autowired
    private CakeService cakeService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Adds a new cake to database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cake added successfully", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "403", description = "Users other than Admins cannot add new cake", content = {@Content(mediaType = "application/json")})
    })
    public ResponseEntity<Cake> addCake(@RequestBody Cake cake) {
        log.info("Adding new cake" + cake.getName());
        Cake newCake = cakeService.addCake(cake);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCake);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Updates cake details in database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cake updated successfully", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Cake not found to update", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "403", description = "Users other than Admins cannot update cake", content = {@Content(mediaType = "application/json")})
    })
    public ResponseEntity<Cake> updateCake(@PathVariable Long id, @RequestBody Cake cake) {
        log.info("Updating Cake Id: " + id);
        Cake updatedCake = cakeService.updateCake(id, cake);
        return ResponseEntity.ok(updatedCake);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Deletes cake from database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cake deleted successfully", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Cake not found in database to update", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "403", description = "Users other than Admins cannot delete cake", content = {@Content(mediaType = "application/json")})
    })
    public ResponseEntity<Void> deleteCake(@PathVariable Long id) {
        log.info("Deleting Cake Id: " + id);
        cakeService.deleteCake(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Gets cake details from database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cake details retrieved successfully", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Cake not found in database to retrieve", content = {@Content(mediaType = "application/json")})
    })
    public ResponseEntity<Cake> getCake(@PathVariable Long id) {
        log.info("Get Cake Details for Id: " + id);
        Cake cake = cakeService.getCakeById(id);
        return ResponseEntity.ok(cake);
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Gets all cakes from database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cake details retrieved successfully", content = {@Content(mediaType = "application/json")})
    })
    public ResponseEntity<List<Cake>> getAllCakes() {
        log.info("Get All Cakes");
        List<Cake> cakes = cakeService.getAllCakes();
        return ResponseEntity.ok(cakes);
    }
}

