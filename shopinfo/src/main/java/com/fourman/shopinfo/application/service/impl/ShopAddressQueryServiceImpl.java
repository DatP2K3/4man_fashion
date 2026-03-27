package com.fourman.shopinfo.application.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fourman.common.dto.response.ShopAddressDTO;
import com.fourman.shopinfo.application.dto.ShopAddressDTOMapper;
import com.fourman.shopinfo.application.service.ShopAddressQueryService;
import com.fourman.shopinfo.infrastructure.persistence.entity.ShopAddressEntity;
import com.fourman.shopinfo.infrastructure.persistence.repository.ShopAddressEntityRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShopAddressQueryServiceImpl implements ShopAddressQueryService {
    private final ShopAddressEntityRepository shopAddressEntityRepository;
    private final ShopAddressDTOMapper shopAddressDTOMapper;

    @Override
    public List<ShopAddressDTO> getAllShopAddresses() {
        List<ShopAddressEntity> shopAddressEntities = shopAddressEntityRepository.findAll();
        return shopAddressDTOMapper.entitiesToDTOs(shopAddressEntities);
    }
}
