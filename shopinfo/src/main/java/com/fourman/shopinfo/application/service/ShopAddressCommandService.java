package com.fourman.shopinfo.application.service;

import com.fourman.common.dto.response.ShopAddressDTO;
import com.fourman.shopinfo.application.dto.request.CreateOrUpdateShopAddressRequest;

public interface ShopAddressCommandService {
    ShopAddressDTO create(CreateOrUpdateShopAddressRequest request);

    ShopAddressDTO update(CreateOrUpdateShopAddressRequest request);
}
