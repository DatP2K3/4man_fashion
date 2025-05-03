package com.evo.cart.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;

import com.evo.cart.domain.Cart;
import com.evo.cart.infrastructure.persistence.entity.CartEntity;
import com.evo.common.mapper.EntityMapper;

@Mapper(componentModel = "Spring")
public interface CartEntityMapper extends EntityMapper<Cart, CartEntity> {}
