package com.fourman.product.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;

import com.fourman.common.mapper.EntityMapper;
import com.fourman.product.domain.Product;
import com.fourman.product.infrastructure.persistence.entity.ProductEntity;

@Mapper(componentModel = "Spring")
public interface ProductEntityMapper extends EntityMapper<Product, ProductEntity> {}
