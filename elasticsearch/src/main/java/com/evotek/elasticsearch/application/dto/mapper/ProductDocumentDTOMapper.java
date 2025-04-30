package com.evotek.elasticsearch.application.dto.mapper;

import com.evotek.elasticsearch.infrastructure.persistence.document.ProductDocumentEntity;
import org.mapstruct.Mapper;

import com.evo.common.dto.response.DTOMapper;
import com.evotek.elasticsearch.application.dto.response.ProductDocumentDTO;
import com.evotek.elasticsearch.domain.ProductDocument;

@Mapper(componentModel = "spring")
public interface ProductDocumentDTOMapper extends DTOMapper<ProductDocumentDTO, ProductDocument, ProductDocumentEntity> {}
