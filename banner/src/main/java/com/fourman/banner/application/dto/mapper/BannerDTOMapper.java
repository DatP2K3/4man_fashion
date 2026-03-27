package com.fourman.banner.application.dto.mapper;

import org.mapstruct.Mapper;

import com.fourman.banner.application.dto.response.BannerDTO;
import com.fourman.banner.domain.Banner;
import com.fourman.banner.infrastructure.persistence.entity.BannerEntity;
import com.fourman.common.dto.response.DTOMapper;

@Mapper(componentModel = "spring")
public interface BannerDTOMapper extends DTOMapper<BannerDTO, Banner, BannerEntity> {}
