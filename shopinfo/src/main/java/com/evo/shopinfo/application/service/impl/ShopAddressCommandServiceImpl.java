package com.evo.shopinfo.application.service.impl;

import com.evo.shopinfo.application.dto.ShopAddressDTOMapper;
import com.evo.shopinfo.application.dto.request.CreateOrUpdateShopAddressRequest;
import com.evo.shopinfo.application.dto.response.ShopAddressDTO;
import com.evo.shopinfo.application.mapper.CommandMapper;
import com.evo.shopinfo.application.service.ShopAddressCommandService;
import com.evo.shopinfo.domain.ShopAddress;
import com.evo.shopinfo.domain.command.CreateUpdateShopAddressCmd;
import com.evo.shopinfo.domain.repository.ShopAddressDomainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
