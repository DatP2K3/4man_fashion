package com.evo.banner.application.dto.mapper;

import org.mapstruct.Mapper;

import com.evo.banner.application.dto.response.BannerDTO;
import com.evo.banner.domain.Banner;
import com.evo.banner.infrastructure.persistence.entity.BannerEntity;
import com.evo.common.dto.response.DTOMapper;

@Mapper(componentModel = "spring")
public interface BannerDTOMapper extends DTOMapper<BannerDTO, Banner, BannerEntity> {}
