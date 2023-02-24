package com.waracle.cakemanager.service;

import com.waracle.cakemanager.entity.Cake;
import com.waracle.cakemanager.exceptions.NotFoundException;
import com.waracle.cakemanager.repository.CakeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CakeServiceImpl implements CakeService {

    @Autowired
    private CakeRepository cakeRepository;

    public List<Cake> getAllCakes() {
        log.info("Get All Cakes");
        return cakeRepository.findAll();
    }

    public Cake getCakeById(Long id) {
        log.info("Get Cake Details for Id: " + id);
        return cakeRepository.findById(id).orElseThrow(() -> new NotFoundException("Cake not found with id " + id));
    }

    public Cake addCake(Cake cake) {
        log.info("Adding new cake: " + cake.getName());
        return cakeRepository.save(cake);
    }

    public Cake updateCake(Long id, Cake updatedCake) {
        log.info("Updating Cake Id: " + id);
        Cake cake = getCakeById(id);
        cake.setName(updatedCake.getName());
        cake.setDescription(updatedCake.getDescription());
        return cakeRepository.save(cake);
    }

    public void deleteCake(Long id) {
        log.info("Deleting Cake Id: " + id);
        Cake cake = getCakeById(id);
        cakeRepository.delete(cake);
    }
}

