package com.evo.cart.infrastructure.persistence.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.evo.cart.infrastructure.persistence.entity.CartItemEntity;

public interface CartItemEntityRepository extends JpaRepository<CartItemEntity, UUID> {
    List<CartItemEntity> findByCartIdIn(List<UUID> cartIds);
}
