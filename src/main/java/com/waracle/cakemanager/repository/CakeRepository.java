package com.waracle.cakemanager.repository;

import com.waracle.cakemanager.entity.CakeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CakeRepository extends JpaRepository<CakeEntity,Long> {
}
