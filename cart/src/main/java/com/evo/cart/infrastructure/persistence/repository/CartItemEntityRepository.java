package com.evo.cart.infrastructure.persistence.repository;

import com.evo.cart.infrastructure.persistence.entity.CartItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface CartItemEntityRepository extends JpaRepository<CartItemEntity, UUID> {
    List<CartItemEntity> findByCartIdIn(List<UUID> cartIds);
}
