package com.fourman.banner.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;

import com.fourman.banner.domain.Banner;
import com.fourman.banner.infrastructure.persistence.entity.BannerEntity;
import com.fourman.common.mapper.EntityMapper;

@Mapper(componentModel = "spring")
public interface BannerEntityMapper extends EntityMapper<Banner, BannerEntity> {}
