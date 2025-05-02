package com.evo.profile.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;

import com.evo.common.mapper.EntityMapper;
import com.evo.profile.domain.ShippingAddress;
import com.evo.profile.infrastructure.persistence.entity.ShippingAddressEntity;

@Mapper(componentModel = "Spring")
public interface ShippingAdressEntityMapper extends EntityMapper<ShippingAddress, ShippingAddressEntity> {}
