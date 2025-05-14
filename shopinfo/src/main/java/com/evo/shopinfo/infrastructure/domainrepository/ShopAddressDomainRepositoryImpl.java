package com.evo.shopinfo.infrastructure.domainrepository;

import java.util.*;

import org.springframework.stereotype.Repository;

import com.evo.common.repository.AbstractDomainRepository;
import com.evo.shopinfo.domain.ShopAddress;
import com.evo.shopinfo.domain.repository.ShopAddressDomainRepository;
import com.evo.shopinfo.infrastructure.persistence.entity.ShopAddressEntity;
import com.evo.shopinfo.infrastructure.persistence.mapper.ShopAddressEntityMapper;
import com.evo.shopinfo.infrastructure.persistence.repository.ShopAddressEntityRepository;
import com.evo.shopinfo.infrastructure.support.exception.AppErrorCode;
import com.evo.shopinfo.infrastructure.support.exception.AppException;

@Repository
public class ShopAddressDomainRepositoryImpl extends AbstractDomainRepository<ShopAddress, ShopAddressEntity, UUID>
        implements ShopAddressDomainRepository {
    private final ShopAddressEntityRepository shopAddressEntityRepository;
    private final ShopAddressEntityMapper shopAddressEntityMapper;

    public ShopAddressDomainRepositoryImpl(
            ShopAddressEntityRepository shopAddressEntityRepository, ShopAddressEntityMapper shopAddressEntityMapper) {
        super(shopAddressEntityRepository, shopAddressEntityMapper);
        this.shopAddressEntityRepository = shopAddressEntityRepository;
        this.shopAddressEntityMapper = shopAddressEntityMapper;
    }

    @Override
    public ShopAddress getById(UUID uuid) {
        ShopAddressEntity shopAddressEntity = shopAddressEntityRepository
                .findById(uuid)
                .orElseThrow(() -> new AppException(AppErrorCode.SHOP_ADDRESS_NOT_FOUND));
        return shopAddressEntityMapper.toDomainModel(shopAddressEntity);
    }
}
