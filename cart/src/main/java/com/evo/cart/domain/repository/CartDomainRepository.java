package com.evo.cart.domain.repository;

import java.util.List;
import java.util.UUID;

import com.evo.cart.domain.Cart;
import com.evo.common.repository.DomainRepository;

public interface CartDomainRepository extends DomainRepository<Cart, UUID> {
    List<Cart> getAll();

    Cart getByUserIdOrNull(java.util.UUID userId);
}
