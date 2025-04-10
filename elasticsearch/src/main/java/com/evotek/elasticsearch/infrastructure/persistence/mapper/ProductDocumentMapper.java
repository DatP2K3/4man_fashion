package com.evotek.elasticsearch.infrastructure.persistence.mapper;

import com.evotek.elasticsearch.domain.ProductDocument;
import com.evotek.elasticsearch.infrastructure.persistence.document.ProductDocumentEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "Spring")
public interface ProductDocumentMapper extends DocumentMapper<ProductDocument, ProductDocumentEntity> {}
