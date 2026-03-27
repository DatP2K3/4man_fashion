package com.fourman.banner.application.mapper;

import org.mapstruct.Mapper;

import com.fourman.banner.application.dto.request.CreateBannerRequest;
import com.fourman.banner.domain.command.CreateBannerCmd;

@Mapper(componentModel = "spring")
public interface CommandMapper {
    CreateBannerCmd from(CreateBannerRequest request);
}
