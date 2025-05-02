package com.evo.product.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;

import com.evo.common.mapper.EntityMapper;
import com.evo.product.domain.TagDescription;
import com.evo.product.infrastructure.persistence.entity.TagDescriptionEntity;

@Mapper(componentModel = "Spring")
public interface TagDescriptionEntityMapper extends EntityMapper<TagDescription, TagDescriptionEntity> {}
