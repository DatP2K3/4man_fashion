package com.evo.shopinfo.application.service;

import java.util.List;

import com.evo.common.dto.response.ShopAddressDTO;

public interface ShopAddressQueryService {
    List<ShopAddressDTO> getAllShopAddresses();
}
