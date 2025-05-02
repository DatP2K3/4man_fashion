package com.evo.product.infrastructure.persistence.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.evo.product.infrastructure.persistence.entity.ProductEntity;
import org.springframework.data.jpa.repository.Query;

public interface ProductEntityRepository extends JpaRepository<ProductEntity, UUID> {
    @Query("SELECT p FROM ProductEntity p WHERE p.hidden = false")
    List<ProductEntity> getAll();
}
