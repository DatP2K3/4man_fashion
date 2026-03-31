package com.fourman.publicinfo.shopinfo.application.service;

import com.fourman.common.dto.response.ShopAddressDTO;
import com.fourman.publicinfo.shopinfo.application.dto.request.CreateOrUpdateShopAddressRequest;

public interface ShopAddressCommandService {
    ShopAddressDTO create(CreateOrUpdateShopAddressRequest request);

    ShopAddressDTO update(CreateOrUpdateShopAddressRequest request);
}
