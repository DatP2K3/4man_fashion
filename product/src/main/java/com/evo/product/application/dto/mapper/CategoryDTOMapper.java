package com.evo.product.application.dto.mapper;

import com.evo.common.dto.response.DTOMapper;
import com.evo.product.application.dto.response.CategoryDTO;
import com.evo.product.domain.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {TagDescriptionDTOMapper.class})
public interface CategoryDTOMapper extends DTOMapper<CategoryDTO, Category> {}