package com.evo.product.domain.repository;

import com.evo.common.repository.DomainRepository;
import com.evo.product.domain.Category;

import java.util.List;
import java.util.UUID;

public interface CategoryDomainRepository extends DomainRepository<Category, UUID> {
    List<Category> getAll();
}
