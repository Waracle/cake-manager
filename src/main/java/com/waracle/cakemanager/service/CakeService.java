package com.waracle.cakemanager.service;

import com.waracle.cakemanager.entity.Cake;

import java.util.List;

public interface CakeService {

    List<Cake> getAllCakes();

    Cake getCakeById(Long id);

    Cake addCake(Cake cake);

    Cake updateCake(Long id, Cake updatedCake);

    void deleteCake(Long id);
}
