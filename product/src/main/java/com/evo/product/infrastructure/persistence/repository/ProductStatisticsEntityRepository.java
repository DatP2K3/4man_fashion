package com.evo.product.infrastructure.persistence.repository;

import com.evo.product.infrastructure.persistence.entity.ProductStatisticsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductStatisticsEntityRepository extends JpaRepository<ProductStatisticsEntity, UUID> {
}
