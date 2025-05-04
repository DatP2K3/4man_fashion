package com.evo.shopinfo.application.service;

import com.evo.common.dto.response.ShopAddressDTO;

import java.util.List;

public interface ShopAddressQueryService {
    List<ShopAddressDTO> getAllShopAddresses();
}
