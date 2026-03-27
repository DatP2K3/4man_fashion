package com.fourman.shopinfo.application.mapper;

import org.mapstruct.Mapper;

import com.fourman.shopinfo.application.dto.request.CreateOrUpdateShopAddressRequest;
import com.fourman.shopinfo.domain.command.CreateUpdateShopAddressCmd;

@Mapper(componentModel = "spring")
public interface CommandMapper {
    CreateUpdateShopAddressCmd from(CreateOrUpdateShopAddressRequest request);
}
