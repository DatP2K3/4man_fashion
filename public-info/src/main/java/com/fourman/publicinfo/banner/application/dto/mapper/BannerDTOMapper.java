package com.fourman.publicinfo.banner.application.dto.mapper;

import org.mapstruct.Mapper;

import com.fourman.publicinfo.banner.application.dto.response.BannerDTO;
import com.fourman.publicinfo.banner.domain.Banner;
import com.fourman.publicinfo.banner.infrastructure.persistence.entity.BannerEntity;
import com.fourman.common.dto.response.DTOMapper;

@Mapper(componentModel = "spring")
public interface BannerDTOMapper extends DTOMapper<BannerDTO, Banner, BannerEntity> {}
