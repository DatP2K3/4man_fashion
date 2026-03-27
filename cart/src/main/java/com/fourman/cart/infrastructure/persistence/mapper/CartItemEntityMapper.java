package com.fourman.cart.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;

import com.fourman.cart.domain.CartItem;
import com.fourman.cart.infrastructure.persistence.entity.CartItemEntity;
import com.fourman.common.mapper.EntityMapper;

@Mapper(componentModel = "Spring")
public interface CartItemEntityMapper extends EntityMapper<CartItem, CartItemEntity> {}
