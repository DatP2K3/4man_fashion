package com.fourman.shopinfo.application.service.impl;

import org.springframework.stereotype.Service;

import com.fourman.common.dto.response.ShopAddressDTO;
import com.fourman.shopinfo.application.dto.ShopAddressDTOMapper;
import com.fourman.shopinfo.application.dto.request.CreateOrUpdateShopAddressRequest;
import com.fourman.shopinfo.application.mapper.CommandMapper;
import com.fourman.shopinfo.application.service.ShopAddressCommandService;
import com.fourman.shopinfo.domain.ShopAddress;
import com.fourman.shopinfo.domain.command.CreateUpdateShopAddressCmd;
import com.fourman.shopinfo.domain.repository.ShopAddressDomainRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShopAddressCommandServiceImpl implements ShopAddressCommandService {
    private final ShopAddressDomainRepository shopAddressDomainRepository;
    private final CommandMapper commandMapper;
    private final ShopAddressDTOMapper shopAddressDTOMapper;

    @Override
    public ShopAddressDTO create(CreateOrUpdateShopAddressRequest request) {
        CreateUpdateShopAddressCmd cmd = commandMapper.from(request);
        ShopAddress shopAddress = new ShopAddress(cmd);
        shopAddress = shopAddressDomainRepository.save(shopAddress);
        return shopAddressDTOMapper.domainModelToDTO(shopAddress);
    }

    @Override
    public ShopAddressDTO update(CreateOrUpdateShopAddressRequest request) {
        CreateUpdateShopAddressCmd cmd = commandMapper.from(request);
        ShopAddress shopAddress = shopAddressDomainRepository.getById(cmd.getId());
        shopAddress.update(cmd);
        shopAddressDomainRepository.save(shopAddress);
        return shopAddressDTOMapper.domainModelToDTO(shopAddress);
    }
}
