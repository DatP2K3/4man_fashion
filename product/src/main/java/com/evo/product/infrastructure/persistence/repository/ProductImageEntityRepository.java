package com.evo.product.infrastructure.persistence.repository;

import com.evo.product.infrastructure.persistence.entity.ProductImageEntity;
import com.evo.product.infrastructure.persistence.entity.ProductVariantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProductImageEntityRepository extends JpaRepository<ProductImageEntity, UUID> {
    List<ProductImageEntity> findByProductIdIn(List<UUID> productIds);
}
