package com.evo.cart.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;

import com.evo.cart.domain.CartItem;
import com.evo.cart.infrastructure.persistence.entity.CartItemEntity;
import com.evo.common.mapper.EntityMapper;

@Mapper(componentModel = "Spring")
public interface CartItemEntityMapper extends EntityMapper<CartItem, CartItemEntity> {}
