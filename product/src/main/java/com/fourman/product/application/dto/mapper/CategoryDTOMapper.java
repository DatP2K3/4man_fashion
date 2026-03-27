package com.fourman.product.application.dto.mapper;

import org.mapstruct.Mapper;

import com.fourman.common.dto.response.DTOMapper;
import com.fourman.product.application.dto.response.CategoryDTO;
import com.fourman.product.domain.Category;
import com.fourman.product.infrastructure.persistence.entity.CategoryEntity;

@Mapper(
        componentModel = "spring",
        uses = {TagDescriptionDTOMapper.class})
public interface CategoryDTOMapper extends DTOMapper<CategoryDTO, Category, CategoryEntity> {}
