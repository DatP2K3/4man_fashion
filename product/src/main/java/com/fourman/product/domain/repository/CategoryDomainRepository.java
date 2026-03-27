package com.fourman.product.domain.repository;

import java.util.List;
import java.util.UUID;

import com.fourman.common.repository.DomainRepository;
import com.fourman.product.domain.Category;

public interface CategoryDomainRepository extends DomainRepository<Category, UUID> {
    List<Category> getAll();
}
