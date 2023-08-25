package com.philldenness.cakemanager.controller;

import java.util.List;

import com.philldenness.cakemanager.dto.CakeDTO;
import com.philldenness.cakemanager.service.CakeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CakeController {

    private final CakeService cakeService;

    @GetMapping("/cakes")
    public List<CakeDTO> getAllCakes() {
        return cakeService.getCakes();
    }
}
