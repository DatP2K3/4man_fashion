package com.evotek.elasticsearch.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;

import com.evotek.elasticsearch.domain.ProductDocument;
import com.evotek.elasticsearch.infrastructure.persistence.document.ProductDocumentEntity;

@Mapper(componentModel = "Spring")
public interface ProductDocumentMapper extends DocumentMapper<ProductDocument, ProductDocumentEntity> {}
