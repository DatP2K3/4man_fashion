package com.evo.banner.infrastructure.persistence.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.evo.banner.infrastructure.persistence.entity.BannerEntity;

public interface BannerEntityRepository extends JpaRepository<BannerEntity, UUID> {
    List<BannerEntity> findByDeletedFalse();
}
