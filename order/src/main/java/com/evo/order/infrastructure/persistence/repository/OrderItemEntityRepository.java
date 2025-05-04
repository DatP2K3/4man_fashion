package com.evo.order.infrastructure.persistence.repository;

import com.evo.order.infrastructure.persistence.entity.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderItemEntityRepository extends JpaRepository<OrderItemEntity, UUID> {
}
