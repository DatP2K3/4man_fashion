package com.evo.product.infrastructure.persistence.repository;

import com.evo.product.infrastructure.persistence.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductEntityRepository extends JpaRepository<ProductEntity, UUID> {
}
