package com.evo.shopinfo.application.dto;

import org.mapstruct.Mapper;

import com.evo.common.dto.response.DTOMapper;
import com.evo.common.dto.response.ShopAddressDTO;
import com.evo.shopinfo.domain.ShopAddress;
import com.evo.shopinfo.infrastructure.persistence.entity.ShopAddressEntity;

@Mapper(componentModel = "spring")
public interface ShopAddressDTOMapper extends DTOMapper<ShopAddressDTO, ShopAddress, ShopAddressEntity> {}
