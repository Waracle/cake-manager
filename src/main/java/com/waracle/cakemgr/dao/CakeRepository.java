package com.waracle.cakemgr.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CakeRepository extends JpaRepository<Cake, UUID> {

}
