package com.evo.cart.infrastructure.persistence.repository;

import com.evo.cart.infrastructure.persistence.entity.CartItemEntity;
import com.evo.common.repository.DomainRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CartItemRepository extends JpaRepository<CartItemEntity, UUID> {
}
