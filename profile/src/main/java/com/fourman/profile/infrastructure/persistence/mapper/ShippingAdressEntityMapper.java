package com.fourman.profile.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;

import com.fourman.common.mapper.EntityMapper;
import com.fourman.profile.domain.ShippingAddress;
import com.fourman.profile.infrastructure.persistence.entity.ShippingAddressEntity;

@Mapper(componentModel = "Spring")
public interface ShippingAdressEntityMapper extends EntityMapper<ShippingAddress, ShippingAddressEntity> {}
