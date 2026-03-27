package com.fourman.product.infrastructure.persistence.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fourman.product.infrastructure.persistence.entity.ProductImageEntity;

public interface ProductImageEntityRepository extends JpaRepository<ProductImageEntity, UUID> {
    List<ProductImageEntity> findByProductIdIn(List<UUID> productIds);
}
