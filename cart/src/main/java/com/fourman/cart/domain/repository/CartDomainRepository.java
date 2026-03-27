package com.fourman.cart.domain.repository;

import java.util.List;
import java.util.UUID;

import com.fourman.cart.domain.Cart;
import com.fourman.common.repository.DomainRepository;

public interface CartDomainRepository extends DomainRepository<Cart, UUID> {
    List<Cart> getAll();

    Cart getByUserIdOrNull(java.util.UUID userId);
}
