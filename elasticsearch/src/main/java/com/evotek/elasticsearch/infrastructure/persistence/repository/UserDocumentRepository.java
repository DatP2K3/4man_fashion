package com.evotek.elasticsearch.infrastructure.persistence.repository;

import java.util.UUID;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.evotek.elasticsearch.infrastructure.persistence.document.UserDocument;

public interface UserDocumentRepository extends ElasticsearchRepository<UserDocument, UUID> {}
