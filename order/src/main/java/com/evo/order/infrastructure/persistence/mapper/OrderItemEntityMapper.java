package com.evo.order.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;

import com.evo.common.mapper.EntityMapper;
import com.evo.order.domain.OrderItem;
import com.evo.order.infrastructure.persistence.entity.OrderItemEntity;

@Mapper(componentModel = "spring")
public interface OrderItemEntityMapper extends EntityMapper<OrderItem, OrderItemEntity> {}
