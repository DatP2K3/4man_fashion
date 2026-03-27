package com.fourman.product.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;

import com.fourman.common.mapper.EntityMapper;
import com.fourman.product.domain.TagDescription;
import com.fourman.product.infrastructure.persistence.entity.TagDescriptionEntity;

@Mapper(componentModel = "Spring")
public interface TagDescriptionEntityMapper extends EntityMapper<TagDescription, TagDescriptionEntity> {}
