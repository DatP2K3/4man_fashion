package com.evo.product.infrastructure.persistence.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.evo.product.infrastructure.persistence.entity.ProductVariantEntity;

public interface ProductVariantEntityRepository extends JpaRepository<ProductVariantEntity, UUID> {
    List<ProductVariantEntity> findByProductIdIn(List<UUID> productIds);
}
