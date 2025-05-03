package com.evo.product.infrastructure.persistence.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.evo.common.enums.DiscountStatus;
import com.evo.product.infrastructure.persistence.entity.DiscountEntity;

public interface DiscountEntityRepository extends JpaRepository<DiscountEntity, UUID> {
    @Query("SELECT d FROM DiscountEntity d WHERE d.productId IN :productIds AND d.status NOT IN :discountStatuses")
    List<DiscountEntity> findByProductIdsAndStatusNotIn(
            List<UUID> productIds, @Param("discountStatuses") List<DiscountStatus> discountStatuses);

    @Query("SELECT d FROM DiscountEntity d WHERE d.status NOT IN :discountStatuses")
    List<DiscountEntity> findAllByStatusNotIn(List<DiscountStatus> discountStatuses);
}
