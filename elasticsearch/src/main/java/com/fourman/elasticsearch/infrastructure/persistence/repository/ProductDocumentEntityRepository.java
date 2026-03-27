package com.fourman.elasticsearch.infrastructure.persistence.repository;

import java.util.UUID;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.fourman.elasticsearch.infrastructure.persistence.document.ProductDocumentEntity;

public interface ProductDocumentEntityRepository extends ElasticsearchRepository<ProductDocumentEntity, UUID> {}
