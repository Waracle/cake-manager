package com.philldenness.cakemanager.controller;

import java.util.List;

import com.philldenness.cakemanager.dto.CakeDTO;
import com.philldenness.cakemanager.service.CakeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CakeController {

    private final CakeService cakeService;

    @GetMapping("/cakes")
    @Operation(summary = "Get all cakes")
    public List<CakeDTO> getAllCakes() {
        return cakeService.getCakes();
    }
}
