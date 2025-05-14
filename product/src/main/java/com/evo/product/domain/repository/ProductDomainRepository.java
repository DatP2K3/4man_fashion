package com.evo.product.domain.repository;

import java.util.List;
import java.util.UUID;

import com.evo.common.repository.DomainRepository;
import com.evo.product.domain.Product;

public interface ProductDomainRepository extends DomainRepository<Product, UUID> {
    List<Product> getAllProductsWithNoDiscount();

    List<Product> getAll();
}
