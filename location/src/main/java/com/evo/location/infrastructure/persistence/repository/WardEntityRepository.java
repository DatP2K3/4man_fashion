package com.evo.location.infrastructure.persistence.repository;

import com.evo.location.infrastructure.persistence.entity.WardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WardEntityRepository extends JpaRepository<WardEntity, String> {
    @Query("SELECT w FROM WardEntity w WHERE w.districtId = :districtId")
    List<WardEntity> getWardsByDistrictId(@Param("districtId") int districtId);
}
