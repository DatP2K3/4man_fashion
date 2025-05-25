package com.evo.location.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.evo.location.entity.DistrictEntity;

public interface DistrictEntityRepository extends JpaRepository<DistrictEntity, Integer> {
    @Query("SELECT d FROM DistrictEntity d WHERE d.provinceId = :provinceId")
    List<DistrictEntity> getDistrictsByProvinceId(@Param("provinceId") int provinceId);
}
