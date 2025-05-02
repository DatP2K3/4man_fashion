package com.evo.cart.application.dto.mapper;

import com.evo.cart.application.dto.response.CartDTO;
import com.evo.cart.domain.Cart;
import com.evo.cart.infrastructure.persistence.entity.CartEntity;
import com.evo.common.dto.response.DTOMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartDTOMapper extends DTOMapper<CartDTO, Cart, CartEntity> {
}
