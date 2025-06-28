package com.evo.shopinfo.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;

import com.evo.common.mapper.EntityMapper;
import com.evo.shopinfo.domain.ShopAddress;
import com.evo.shopinfo.infrastructure.persistence.entity.ShopAddressEntity;

@Mapper(componentModel = "spring")
public interface ShopAddressEntityMapper extends EntityMapper<ShopAddress, ShopAddressEntity> {}
