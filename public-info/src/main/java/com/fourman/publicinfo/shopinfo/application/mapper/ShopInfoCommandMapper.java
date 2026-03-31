package com.fourman.publicinfo.shopinfo.application.mapper;

import org.mapstruct.Mapper;

import com.fourman.publicinfo.shopinfo.application.dto.request.CreateOrUpdateShopAddressRequest;
import com.fourman.publicinfo.shopinfo.domain.command.CreateUpdateShopAddressCmd;

@Mapper(componentModel = "spring")
public interface ShopInfoCommandMapper {
    CreateUpdateShopAddressCmd from(CreateOrUpdateShopAddressRequest request);
}
