package com.evo.product.application.dto.mapper;

import org.mapstruct.Mapper;

import com.evo.common.dto.response.DTOMapper;
import com.evo.product.application.dto.response.TagDescriptionDTO;
import com.evo.product.domain.TagDescription;
import com.evo.product.infrastructure.persistence.entity.TagDescriptionEntity;

@Mapper(componentModel = "spring")
public interface TagDescriptionDTOMapper extends DTOMapper<TagDescriptionDTO, TagDescription, TagDescriptionEntity> {}
