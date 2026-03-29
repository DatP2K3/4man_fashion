package com.fourman.shopinfo.application.service.impl.command;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fourman.common.dto.response.ShopAddressDTO;
import com.fourman.shopinfo.application.dto.ShopAddressDTOMapper;
import com.fourman.shopinfo.application.dto.request.CreateOrUpdateShopAddressRequest;
import com.fourman.shopinfo.application.mapper.CommandMapper;
import com.fourman.shopinfo.application.service.ShopAddressCommandService;
import com.fourman.shopinfo.domain.ShopAddress;
import com.fourman.shopinfo.domain.command.CreateUpdateShopAddressCmd;
import com.fourman.shopinfo.domain.repository.ShopAddressDomainRepository;

import lombok.RequiredArgsConstructor;

@Transactional
@Service
@RequiredArgsConstructor
public class ShopAddressCommandServiceImpl implements ShopAddressCommandService {
    private final ShopAddressDomainRepository shopAddressDomainRepository;
    private final CommandMapper commandMapper;
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
