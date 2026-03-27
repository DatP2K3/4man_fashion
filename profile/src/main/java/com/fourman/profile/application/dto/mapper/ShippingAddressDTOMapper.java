package com.fourman.profile.application.dto.mapper;

import org.mapstruct.Mapper;

import com.fourman.common.dto.response.DTOMapper;
import com.fourman.common.dto.response.ShippingAddressDTO;
import com.fourman.profile.domain.ShippingAddress;
import com.fourman.profile.infrastructure.persistence.entity.ShippingAddressEntity;

@Mapper(componentModel = "spring")
public interface ShippingAddressDTOMapper
        extends DTOMapper<ShippingAddressDTO, ShippingAddress, ShippingAddressEntity> {}
