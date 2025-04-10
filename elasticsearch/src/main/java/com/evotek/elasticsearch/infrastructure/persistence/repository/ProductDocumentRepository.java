package com.evotek.elasticsearch.infrastructure.persistence.repository;

import java.util.UUID;

import com.evotek.elasticsearch.infrastructure.persistence.document.ProductDocumentEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ProductDocumentRepository extends ElasticsearchRepository<ProductDocumentEntity, UUID> {}
