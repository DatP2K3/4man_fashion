package com.fourman.publicinfo.banner.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;

import com.fourman.publicinfo.banner.domain.Banner;
import com.fourman.publicinfo.banner.infrastructure.persistence.entity.BannerEntity;
import com.fourman.common.mapper.EntityMapper;

@Mapper(componentModel = "spring")
public interface BannerEntityMapper extends EntityMapper<Banner, BannerEntity> {}
