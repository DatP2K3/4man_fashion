package com.evo.location.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.evo.location.infrastructure.persistence.entity.ProvinceEntity;

public interface ProvinceEntityRepository extends JpaRepository<ProvinceEntity, Integer> {}
