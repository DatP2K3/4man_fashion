package com.evo.banner.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;

import com.evo.banner.domain.Banner;
import com.evo.banner.infrastructure.persistence.entity.BannerEntity;
import com.evo.common.mapper.EntityMapper;

@Mapper(componentModel = "Spring")
public interface BannerEntityMapper extends EntityMapper<Banner, BannerEntity> {}
