package com.evo.cart.infrastructure.persistence.mapper;

import com.evo.cart.domain.Cart;
import com.evo.cart.domain.CartItem;
import com.evo.cart.infrastructure.persistence.entity.CartEntity;
import com.evo.cart.infrastructure.persistence.entity.CartItemEntity;
import com.evo.common.mapper.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "Spring")
public interface CartItemEntityMapper extends EntityMapper<CartItem, CartItemEntity> {}
