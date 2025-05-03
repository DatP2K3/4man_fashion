package com.evo.cart.application.dto.mapper;

import org.mapstruct.Mapper;

import com.evo.cart.application.dto.response.CartDTO;
import com.evo.cart.domain.Cart;
import com.evo.cart.infrastructure.persistence.entity.CartEntity;
import com.evo.common.dto.response.DTOMapper;

@Mapper(componentModel = "spring")
public interface CartDTOMapper extends DTOMapper<CartDTO, Cart, CartEntity> {}
