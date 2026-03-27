package com.fourman.cart.application.dto.mapper;

import org.mapstruct.Mapper;

import com.fourman.cart.domain.Cart;
import com.fourman.cart.infrastructure.persistence.entity.CartEntity;
import com.fourman.common.dto.response.CartDTO;
import com.fourman.common.dto.response.DTOMapper;

@Mapper(componentModel = "spring")
public interface CartDTOMapper extends DTOMapper<CartDTO, Cart, CartEntity> {}
