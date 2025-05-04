package com.evo.shopinfo.application.service;

import com.evo.shopinfo.application.dto.request.CreateOrUpdateShopAddressRequest;
import com.evo.common.dto.response.ShopAddressDTO;

public interface ShopAddressCommandService {
    ShopAddressDTO create(CreateOrUpdateShopAddressRequest request);
    ShopAddressDTO update(CreateOrUpdateShopAddressRequest request);
}
