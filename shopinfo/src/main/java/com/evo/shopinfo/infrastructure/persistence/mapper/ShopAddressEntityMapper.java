package com.evo.shopinfo.infrastructure.persistence.mapper;

import com.evo.common.mapper.EntityMapper;
import com.evo.shopinfo.domain.ShopAddress;
import com.evo.shopinfo.infrastructure.persistence.entity.ShopAddressEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ShopAddressEntityMapper extends EntityMapper<ShopAddress, ShopAddressEntity> {
}
