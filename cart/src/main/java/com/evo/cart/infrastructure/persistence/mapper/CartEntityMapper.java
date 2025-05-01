package com.evo.product.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;

import com.evo.common.mapper.EntityMapper;
import com.evo.product.domain.Category;
import com.evo.product.infrastructure.persistence.entity.CategoryEntity;

@Mapper(componentModel = "Spring")
public interface CategoryEntityMapper extends EntityMapper<Category, CategoryEntity> {}
