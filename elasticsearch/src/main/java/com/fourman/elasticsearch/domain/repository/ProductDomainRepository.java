package com.fourman.elasticsearch.domain.repository;

import java.util.UUID;

import com.fourman.elasticsearch.domain.ProductDocument;
import com.fourman.elasticsearch.domain.repository.base.DocumentDomainRepository;

public interface ProductDomainRepository extends DocumentDomainRepository<ProductDocument, UUID> {
    void deleteById(UUID userId);
}
