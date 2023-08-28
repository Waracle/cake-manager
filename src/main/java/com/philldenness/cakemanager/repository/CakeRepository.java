package com.philldenness.cakemanager.repository;

import com.philldenness.cakemanager.entity.CakeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CakeRepository extends JpaRepository<CakeEntity, Long> {
}
