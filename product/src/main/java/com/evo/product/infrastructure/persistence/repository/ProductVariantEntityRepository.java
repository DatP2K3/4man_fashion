package com.evo.product.infrastructure.persistence.repository;

import com.evo.product.infrastructure.persistence.entity.ProductVariantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProductVariantEntityRepository extends JpaRepository<ProductVariantEntity, UUID> {
    List<ProductVariantEntity> findByProductIdIn(List<UUID> productIds);
}
