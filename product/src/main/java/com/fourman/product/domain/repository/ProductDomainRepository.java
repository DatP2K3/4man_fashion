package com.fourman.product.domain.repository;

import java.util.List;
import java.util.UUID;

import com.fourman.common.repository.DomainRepository;
import com.fourman.product.domain.Product;

public interface ProductDomainRepository extends DomainRepository<Product, UUID> {
    List<Product> getAllProductsWithNoDiscount();

    List<Product> getAll();
}
