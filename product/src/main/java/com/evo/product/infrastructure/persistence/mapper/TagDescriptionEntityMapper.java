package com.evo.product.infrastructure.persistence.mapper;

import com.evo.common.mapper.EntityMapper;
import com.evo.product.domain.Category;
import com.evo.product.domain.TagDescription;
import com.evo.product.infrastructure.persistence.entity.CategoryEntity;
import com.evo.product.infrastructure.persistence.entity.TagDescriptionEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "Spring")
public interface TagDescriptionEntityMapper extends EntityMapper<TagDescription, TagDescriptionEntity> {}
