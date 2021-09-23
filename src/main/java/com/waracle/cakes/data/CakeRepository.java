package com.waracle.cakes.data;

import org.springframework.data.repository.CrudRepository;

import com.waracle.cakes.model.entity.Cake;

public interface CakeRepository extends CrudRepository<Cake,Integer> {

}
