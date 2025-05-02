package com.evo.cart.domain.repository;

import com.evo.cart.domain.Cart;
import com.evo.common.repository.DomainRepository;

import java.util.List;
import java.util.UUID;

public interface CartDomainRepository extends DomainRepository<Cart, UUID> {
    List<Cart> getAll();

    Cart getByUserIdOrNull(java.util.UUID userId);
}
