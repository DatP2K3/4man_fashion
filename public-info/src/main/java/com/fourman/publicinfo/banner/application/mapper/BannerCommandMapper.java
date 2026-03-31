package com.fourman.publicinfo.banner.application.mapper;

import org.mapstruct.Mapper;

import com.fourman.publicinfo.banner.application.dto.request.CreateBannerRequest;
import com.fourman.publicinfo.banner.domain.command.CreateBannerCmd;

@Mapper(componentModel = "spring")
public interface BannerCommandMapper {
    CreateBannerCmd from(CreateBannerRequest request);
}
