package com.evo.location.infrastructure.persistence.repository;

import com.evo.location.infrastructure.persistence.entity.DistrictEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DistrictEntityRepository extends JpaRepository<DistrictEntity, Integer> {
    @Query("SELECT d FROM DistrictEntity d WHERE d.provinceId = :provinceId")
    List<DistrictEntity> getDistrictsByProvinceId(@Param("provinceId") int provinceId);
}
