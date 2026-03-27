package com.fourman.product.application.dto.mapper;

import org.mapstruct.Mapper;

import com.fourman.common.dto.response.DTOMapper;
import com.fourman.common.dto.response.ProductImageDTO;
import com.fourman.product.domain.ProductImage;
import com.fourman.product.infrastructure.persistence.entity.ProductImageEntity;

@Mapper(componentModel = "spring")
public interface ProductImageDTOMapper extends DTOMapper<ProductImageDTO, ProductImage, ProductImageEntity> {}
