package com.fourman.product.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;

import com.fourman.common.mapper.EntityMapper;
import com.fourman.product.domain.ProductImage;
import com.fourman.product.infrastructure.persistence.entity.ProductImageEntity;

@Mapper(componentModel = "Spring")
public interface ProductImageEntityMapper extends EntityMapper<ProductImage, ProductImageEntity> {}
