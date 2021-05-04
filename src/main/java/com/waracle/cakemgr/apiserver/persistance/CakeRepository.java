package com.waracle.cakemgr.apiserver.persistance;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CakeRepository extends JpaRepository<CakeEntity, UUID> {
}
