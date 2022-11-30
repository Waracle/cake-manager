package com.waracle.cakemgr.repository;

import com.waracle.cakemgr.entity.CakeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CakeManagerRepository extends JpaRepository<CakeEntity, Long> {

}
