package com.evo.location.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.evo.location.entity.WardEntity;

public interface WardEntityRepository extends JpaRepository<WardEntity, String> {
    @Query("SELECT w FROM WardEntity w WHERE w.districtId = :districtId")
    List<WardEntity> getWardsByDistrictId(@Param("districtId") int districtId);
}
