package com.evo.product.infrastructure.persistence.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.evo.product.infrastructure.persistence.entity.DiscountEntity;

public interface DiscountEntityRepository extends JpaRepository<DiscountEntity, UUID> {
    List<DiscountEntity> findByProductIdIn(List<UUID> productIds);
}
