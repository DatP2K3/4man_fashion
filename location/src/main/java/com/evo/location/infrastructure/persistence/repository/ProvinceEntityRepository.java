package com.evo.location.infrastructure.persistence.repository;

import com.evo.location.infrastructure.persistence.entity.ProvinceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProvinceEntityRepository extends JpaRepository<ProvinceEntity, Integer> {
}
