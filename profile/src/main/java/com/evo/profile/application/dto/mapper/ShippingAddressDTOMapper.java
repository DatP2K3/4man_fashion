package com.evo.profile.application.dto.mapper;

import com.evo.profile.infrastructure.persistence.entity.ShippingAddressEntity;
import org.mapstruct.Mapper;

import com.evo.common.dto.response.DTOMapper;
import com.evo.profile.application.dto.response.ShippingAddressDTO;
import com.evo.profile.domain.ShippingAddress;

@Mapper(componentModel = "spring")
public interface ShippingAddressDTOMapper extends DTOMapper<ShippingAddressDTO, ShippingAddress, ShippingAddressEntity> {}
