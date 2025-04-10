package com.evo.product.infrastructure.persistence.mapper;

import com.evo.common.mapper.EntityMapper;
import com.evo.product.domain.Category;
import com.evo.product.domain.ProductImage;
import com.evo.product.infrastructure.persistence.entity.CategoryEntity;
import com.evo.product.infrastructure.persistence.entity.ProductImageEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "Spring")
public interface ProductImageEntityMapper extends EntityMapper<ProductImage, ProductImageEntity> {
}
