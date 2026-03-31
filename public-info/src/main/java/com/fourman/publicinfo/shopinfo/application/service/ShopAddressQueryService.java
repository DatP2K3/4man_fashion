package com.fourman.publicinfo.shopinfo.application.service;

import java.util.List;

import com.fourman.common.dto.response.ShopAddressDTO;

public interface ShopAddressQueryService {
    List<ShopAddressDTO> getAllShopAddresses();
}
