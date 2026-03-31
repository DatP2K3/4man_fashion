package com.fourman.publicinfo.shopinfo.application.service.impl.command;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fourman.common.dto.response.ShopAddressDTO;
import com.fourman.publicinfo.shopinfo.application.dto.ShopAddressDTOMapper;
import com.fourman.publicinfo.shopinfo.application.dto.request.CreateOrUpdateShopAddressRequest;
import com.fourman.publicinfo.shopinfo.application.mapper.ShopInfoCommandMapper;
import com.fourman.publicinfo.shopinfo.application.service.ShopAddressCommandService;
import com.fourman.publicinfo.shopinfo.domain.ShopAddress;
import com.fourman.publicinfo.shopinfo.domain.command.CreateUpdateShopAddressCmd;
import com.fourman.publicinfo.shopinfo.domain.repository.ShopAddressDomainRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Transactional
@Service
@Slf4j
@RequiredArgsConstructor
public class ShopAddressCommandServiceImpl implements ShopAddressCommandService {
    private final ShopAddressDomainRepository shopAddressDomainRepository;
    private final ShopInfoCommandMapper commandMapper;
    private final ShopAddressDTOMapper shopAddressDTOMapper;

    @Override
    @CacheEvict(value = "shopAddresses", allEntries = true)
    public ShopAddressDTO create(CreateOrUpdateShopAddressRequest request) {
        CreateUpdateShopAddressCmd cmd = commandMapper.from(request);
        ShopAddress shopAddress = new ShopAddress(cmd);
        shopAddress = shopAddressDomainRepository.save(shopAddress);
        return shopAddressDTOMapper.domainModelToDTO(shopAddress);
    }

    @Override
    @CacheEvict(value = "shopAddresses", allEntries = true)
    public ShopAddressDTO update(CreateOrUpdateShopAddressRequest request) {
        CreateUpdateShopAddressCmd cmd = commandMapper.from(request);
        ShopAddress shopAddress = shopAddressDomainRepository.getById(cmd.getId());
        shopAddress.update(cmd);
        shopAddressDomainRepository.save(shopAddress);
        return shopAddressDTOMapper.domainModelToDTO(shopAddress);
    }
}
