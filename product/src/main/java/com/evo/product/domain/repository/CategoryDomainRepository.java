package com.evo.product.domain.repository;

import java.util.List;
import java.util.UUID;

import com.evo.common.repository.DomainRepository;
import com.evo.product.domain.Category;

public interface CategoryDomainRepository extends DomainRepository<Category, UUID> {
    List<Category> getAll();
}
