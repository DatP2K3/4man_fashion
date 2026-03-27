package com.fourman.product.infrastructure.persistence.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fourman.product.infrastructure.persistence.entity.ProductEntity;

public interface ProductEntityRepository extends JpaRepository<ProductEntity, UUID> {
    @Query("SELECT p FROM ProductEntity p WHERE p.hidden = false")
    List<ProductEntity> getAll();
}
