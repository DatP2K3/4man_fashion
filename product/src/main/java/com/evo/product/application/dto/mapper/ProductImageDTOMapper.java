package com.evo.product.application.dto.mapper;

import com.evo.product.infrastructure.persistence.entity.ProductImageEntity;
import org.mapstruct.Mapper;

import com.evo.common.dto.response.DTOMapper;
import com.evo.common.dto.response.ProductImageDTO;
import com.evo.product.domain.ProductImage;

@Mapper(componentModel = "spring")
public interface ProductImageDTOMapper extends DTOMapper<ProductImageDTO, ProductImage, ProductImageEntity> {}
