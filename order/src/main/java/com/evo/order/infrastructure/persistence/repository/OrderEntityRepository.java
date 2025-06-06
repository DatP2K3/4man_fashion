package com.evo.order.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.evo.common.enums.OrderStatus;
import com.evo.order.infrastructure.persistence.entity.OrderEntity;
import com.evo.order.infrastructure.persistence.repository.custom.OrderEntityRepositoryCustom;

public interface OrderEntityRepository extends JpaRepository<OrderEntity, UUID>, OrderEntityRepositoryCustom {
    Optional<OrderEntity> findByOrderCode(String orderCode);

    @Query("SELECT o FROM OrderEntity o WHERE o.orderStatus IN :orderStatuses")
    List<OrderEntity> getAllOrderWithStatusIn(List<OrderStatus> orderStatuses);

    List<OrderEntity> findByUserId(UUID userId);
}
