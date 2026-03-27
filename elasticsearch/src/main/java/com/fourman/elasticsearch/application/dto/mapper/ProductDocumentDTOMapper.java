package com.fourman.elasticsearch.application.dto.mapper;

import org.mapstruct.Mapper;

import com.fourman.common.dto.response.DTOMapper;
import com.fourman.elasticsearch.application.dto.response.ProductDocumentDTO;
import com.fourman.elasticsearch.domain.ProductDocument;
import com.fourman.elasticsearch.infrastructure.persistence.document.ProductDocumentEntity;

@Mapper(componentModel = "spring")
public interface ProductDocumentDTOMapper
        extends DTOMapper<ProductDocumentDTO, ProductDocument, ProductDocumentEntity> {}
