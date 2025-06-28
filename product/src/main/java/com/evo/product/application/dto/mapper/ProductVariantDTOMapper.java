package com.evo.product.application.dto.mapper;

import org.mapstruct.Mapper;

import com.evo.common.dto.response.DTOMapper;
import com.evo.common.dto.response.ProductVariantDTO;
import com.evo.product.domain.ProductVariant;
import com.evo.product.infrastructure.persistence.entity.ProductVariantEntity;

@Mapper(componentModel = "spring")
public interface ProductVariantDTOMapper extends DTOMapper<ProductVariantDTO, ProductVariant, ProductVariantEntity> {}
