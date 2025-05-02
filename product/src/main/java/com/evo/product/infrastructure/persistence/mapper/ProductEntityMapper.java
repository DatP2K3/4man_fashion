package com.evo.product.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;

import com.evo.common.mapper.EntityMapper;
import com.evo.product.domain.Product;
import com.evo.product.infrastructure.persistence.entity.ProductEntity;

@Mapper(componentModel = "Spring")
public interface ProductEntityMapper extends EntityMapper<Product, ProductEntity> {}
