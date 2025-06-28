package com.evo.banner.application.mapper;

import org.mapstruct.Mapper;

import com.evo.banner.application.dto.request.CreateBannerRequest;
import com.evo.banner.domain.command.CreateBannerCmd;

@Mapper(componentModel = "spring")
public interface CommandMapper {
    CreateBannerCmd from(CreateBannerRequest request);
}
