package com.evo.product.application.dto.mapper;

import com.evo.product.infrastructure.persistence.entity.CategoryEntity;
import org.mapstruct.Mapper;

import com.evo.common.dto.response.DTOMapper;
import com.evo.product.application.dto.response.CategoryDTO;
import com.evo.product.domain.Category;

@Mapper(
        componentModel = "spring",
        uses = {TagDescriptionDTOMapper.class})
public interface CategoryDTOMapper extends DTOMapper<CategoryDTO, Category, CategoryEntity> {}
