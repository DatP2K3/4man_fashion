package com.evo.shopinfo.application.service.impl;

import com.evo.shopinfo.application.dto.ShopAddressDTOMapper;
import com.evo.common.dto.response.ShopAddressDTO;
import com.evo.shopinfo.application.service.ShopAddressQueryService;
import com.evo.shopinfo.infrastructure.persistence.entity.ShopAddressEntity;
import com.evo.shopinfo.infrastructure.persistence.repository.ShopAddressEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
