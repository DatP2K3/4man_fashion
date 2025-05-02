package com.evo.product.application.dto.mapper;

import com.evo.product.infrastructure.persistence.entity.ProductVariantEntity;
import org.mapstruct.Mapper;

import com.evo.common.dto.response.DTOMapper;
import com.evo.common.dto.response.ProductVariantDTO;
import com.evo.product.domain.ProductVariant;

@Mapper(componentModel = "spring")
public interface ProductVariantDTOMapper extends DTOMapper<ProductVariantDTO, ProductVariant, ProductVariantEntity> {}
