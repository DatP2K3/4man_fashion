package com.evotek.elasticsearch.domain.repository;

import java.util.UUID;

import com.evotek.elasticsearch.domain.ProductDocument;
import com.evotek.elasticsearch.infrastructure.domainrepository.DocumentDomainRepository;

public interface ProductDomainRepository extends DocumentDomainRepository<ProductDocument, UUID> {
    void deleteById(UUID userId);
}
