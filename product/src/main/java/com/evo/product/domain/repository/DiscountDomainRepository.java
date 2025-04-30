package com.evo.product.domain.repository;

import java.util.List;
import java.util.UUID;

import com.evo.common.repository.DomainRepository;
import com.evo.product.domain.Discount;

public interface DiscountDomainRepository extends DomainRepository<Discount, UUID> {
    List<Discount> getAll();
}
