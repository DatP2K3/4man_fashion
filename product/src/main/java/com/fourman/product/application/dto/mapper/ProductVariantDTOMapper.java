package com.fourman.product.application.dto.mapper;

import org.mapstruct.Mapper;

import com.fourman.common.dto.response.DTOMapper;
import com.fourman.common.dto.response.ProductVariantDTO;
import com.fourman.product.domain.ProductVariant;
import com.fourman.product.infrastructure.persistence.entity.ProductVariantEntity;

@Mapper(componentModel = "spring")
public interface ProductVariantDTOMapper extends DTOMapper<ProductVariantDTO, ProductVariant, ProductVariantEntity> {}
