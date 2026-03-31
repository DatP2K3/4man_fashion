package com.fourman.publicinfo.shopinfo.application.dto;

import org.mapstruct.Mapper;

import com.fourman.common.dto.response.DTOMapper;
import com.fourman.common.dto.response.ShopAddressDTO;
import com.fourman.publicinfo.shopinfo.domain.ShopAddress;
import com.fourman.publicinfo.shopinfo.infrastructure.persistence.entity.ShopAddressEntity;

@Mapper(componentModel = "spring")
public interface ShopAddressDTOMapper extends DTOMapper<ShopAddressDTO, ShopAddress, ShopAddressEntity> {}
