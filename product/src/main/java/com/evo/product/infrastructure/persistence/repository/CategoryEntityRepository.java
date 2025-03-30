package com.evo.product.infrastructure.persistence.repository;

import com.evo.product.infrastructure.persistence.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryEntityRepository extends JpaRepository<CategoryEntity, UUID> {
}
