package com.fourman.product.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;

import com.fourman.common.mapper.EntityMapper;
import com.fourman.product.domain.ProductVariant;
import com.fourman.product.infrastructure.persistence.entity.ProductVariantEntity;

@Mapper(componentModel = "Spring")
public interface ProductVariantEntityMapper extends EntityMapper<ProductVariant, ProductVariantEntity> {}
