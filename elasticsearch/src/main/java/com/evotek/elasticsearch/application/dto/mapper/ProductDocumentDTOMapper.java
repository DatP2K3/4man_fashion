package com.evotek.elasticsearch.application.dto.mapper;

import org.mapstruct.Mapper;

import com.evo.common.dto.response.DTOMapper;
import com.evotek.elasticsearch.application.dto.response.ProductDocumentDTO;
import com.evotek.elasticsearch.domain.ProductDocument;
import com.evotek.elasticsearch.infrastructure.persistence.document.ProductDocumentEntity;

@Mapper(componentModel = "spring")
public interface ProductDocumentDTOMapper
        extends DTOMapper<ProductDocumentDTO, ProductDocument, ProductDocumentEntity> {}
