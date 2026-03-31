package com.fourman.publicinfo.shopinfo.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;

import com.fourman.common.mapper.EntityMapper;
import com.fourman.publicinfo.shopinfo.domain.ShopAddress;
import com.fourman.publicinfo.shopinfo.infrastructure.persistence.entity.ShopAddressEntity;

@Mapper(componentModel = "spring")
public interface ShopAddressEntityMapper extends EntityMapper<ShopAddress, ShopAddressEntity> {}
