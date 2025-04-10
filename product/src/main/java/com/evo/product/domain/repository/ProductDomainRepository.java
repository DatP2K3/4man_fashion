package com.evo.product.domain.repository;

import com.evo.common.repository.DomainRepository;
import com.evo.product.domain.Product;

import java.util.UUID;

public interface ProductDomainRepository extends DomainRepository<Product, UUID> {
}
