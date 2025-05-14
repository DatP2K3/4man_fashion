package com.evo.order.infrastructure.persistence.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.evo.order.infrastructure.persistence.entity.OrderItemEntity;

public interface OrderItemEntityRepository extends JpaRepository<OrderItemEntity, UUID> {
    List<OrderItemEntity> findByOrderIdIn(List<UUID> orderIds);
}
