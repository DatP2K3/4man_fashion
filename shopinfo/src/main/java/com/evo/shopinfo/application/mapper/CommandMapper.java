package com.evo.shopinfo.application.mapper;

import com.evo.shopinfo.application.dto.request.CreateOrUpdateShopAddressRequest;
import com.evo.shopinfo.domain.command.CreateUpdateShopAddressCmd;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommandMapper {
    CreateUpdateShopAddressCmd from(CreateOrUpdateShopAddressRequest request);
}
