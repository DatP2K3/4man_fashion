package com.evo.shopinfo.application.mapper;

import org.mapstruct.Mapper;

import com.evo.shopinfo.application.dto.request.CreateOrUpdateShopAddressRequest;
import com.evo.shopinfo.domain.command.CreateUpdateShopAddressCmd;

@Mapper(componentModel = "spring")
public interface CommandMapper {
    CreateUpdateShopAddressCmd from(CreateOrUpdateShopAddressRequest request);
}
