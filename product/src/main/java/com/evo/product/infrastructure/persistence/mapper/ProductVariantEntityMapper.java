package com.evo.product.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;

import com.evo.common.mapper.EntityMapper;
import com.evo.product.domain.ProductVariant;
import com.evo.product.infrastructure.persistence.entity.ProductVariantEntity;

@Mapper(componentModel = "Spring")
public interface ProductVariantEntityMapper extends EntityMapper<ProductVariant, ProductVariantEntity> {}
