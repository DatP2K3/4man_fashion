package com.fourman.product.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;

import com.fourman.common.mapper.EntityMapper;
import com.fourman.product.domain.Category;
import com.fourman.product.infrastructure.persistence.entity.CategoryEntity;

@Mapper(componentModel = "Spring")
public interface CategoryEntityMapper extends EntityMapper<Category, CategoryEntity> {}
