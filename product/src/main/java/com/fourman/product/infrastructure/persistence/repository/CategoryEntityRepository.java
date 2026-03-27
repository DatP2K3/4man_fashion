package com.fourman.product.infrastructure.persistence.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fourman.product.infrastructure.persistence.entity.CategoryEntity;

public interface CategoryEntityRepository extends JpaRepository<CategoryEntity, UUID> {
    List<CategoryEntity> findByProductType(String productType);
}
