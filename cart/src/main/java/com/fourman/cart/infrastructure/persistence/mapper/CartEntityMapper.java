package com.fourman.cart.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;

import com.fourman.cart.domain.Cart;
import com.fourman.cart.infrastructure.persistence.entity.CartEntity;
import com.fourman.common.mapper.EntityMapper;

@Mapper(componentModel = "Spring")
public interface CartEntityMapper extends EntityMapper<Cart, CartEntity> {}
