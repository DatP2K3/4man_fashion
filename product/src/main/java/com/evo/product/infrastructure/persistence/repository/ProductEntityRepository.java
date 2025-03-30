package com.evo.product.infrastructure.persistence.repository;

import com.evo.product.infrastructure.persistence.entity.ProductEntity;
import org.hibernate.validator.constraints.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductEntityRepository extends JpaRepository<ProductEntity, UUID> {
}
