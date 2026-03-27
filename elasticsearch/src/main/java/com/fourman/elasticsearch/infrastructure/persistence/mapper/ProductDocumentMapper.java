package com.fourman.elasticsearch.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;

import com.fourman.elasticsearch.domain.ProductDocument;
import com.fourman.elasticsearch.infrastructure.persistence.document.ProductDocumentEntity;

@Mapper(componentModel = "Spring")
public interface ProductDocumentMapper extends DocumentMapper<ProductDocument, ProductDocumentEntity> {}
