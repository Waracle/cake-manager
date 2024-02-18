package com.waracle.cakemgr.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.waracle.cakemgr.model.CakeEntity;

@Repository
public interface CakeRepository extends JpaRepository<CakeEntity, Integer> {

}
