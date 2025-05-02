package com.evo.product.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;

import com.evo.common.mapper.EntityMapper;
import com.evo.product.domain.ProductImage;
import com.evo.product.infrastructure.persistence.entity.ProductImageEntity;

@Mapper(componentModel = "Spring")
public interface ProductImageEntityMapper extends EntityMapper<ProductImage, ProductImageEntity> {}
