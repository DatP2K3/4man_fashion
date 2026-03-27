package com.fourman.product.application.dto.mapper;

import org.mapstruct.Mapper;

import com.fourman.common.dto.response.DTOMapper;
import com.fourman.product.application.dto.response.TagDescriptionDTO;
import com.fourman.product.domain.TagDescription;
import com.fourman.product.infrastructure.persistence.entity.TagDescriptionEntity;

@Mapper(componentModel = "spring")
public interface TagDescriptionDTOMapper extends DTOMapper<TagDescriptionDTO, TagDescription, TagDescriptionEntity> {}
