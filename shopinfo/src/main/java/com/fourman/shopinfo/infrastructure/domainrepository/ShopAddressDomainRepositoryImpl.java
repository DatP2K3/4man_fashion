package com.fourman.shopinfo.infrastructure.domainrepository;

import java.util.*;

import org.springframework.stereotype.Repository;

import com.fourman.common.exception.ResponseException;
import com.fourman.common.repository.AbstractDomainRepository;
import com.fourman.shopinfo.domain.ShopAddress;
import com.fourman.shopinfo.domain.repository.ShopAddressDomainRepository;
import com.fourman.shopinfo.infrastructure.persistence.entity.ShopAddressEntity;
import com.fourman.shopinfo.infrastructure.persistence.mapper.ShopAddressEntityMapper;
import com.fourman.shopinfo.infrastructure.persistence.repository.ShopAddressEntityRepository;
import com.fourman.shopinfo.infrastructure.support.exception.NotFoundError;

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
                .orElseThrow(() -> new ResponseException(NotFoundError.SHOP_ADDRESS_NOT_FOUND));
        return shopAddressEntityMapper.toDomainModel(shopAddressEntity);
    }
}
