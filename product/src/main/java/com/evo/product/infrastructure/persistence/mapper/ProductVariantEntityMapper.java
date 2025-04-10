package com.evo.product.infrastructure.persistence.mapper;

import com.evo.common.mapper.EntityMapper;
import com.evo.product.domain.Product;
import com.evo.product.domain.ProductVariant;
import com.evo.product.infrastructure.persistence.entity.ProductEntity;
import com.evo.product.infrastructure.persistence.entity.ProductVariantEntity;
import org.mapstruct.Mapper;

import java.util.Optional;
import java.util.UUID;

@Mapper(componentModel = "Spring")
public interface ProductVariantEntityMapper extends EntityMapper<ProductVariant, ProductVariantEntity> {
}
