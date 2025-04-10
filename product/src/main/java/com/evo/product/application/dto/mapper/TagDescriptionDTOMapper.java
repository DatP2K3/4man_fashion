package com.evo.product.application.dto.mapper;

import com.evo.common.dto.response.DTOMapper;
import com.evo.product.application.dto.response.TagDescriptionDTO;
import com.evo.product.domain.TagDescription;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TagDescriptionDTOMapper extends DTOMapper<TagDescriptionDTO, TagDescription> {}
