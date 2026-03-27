package com.fourman.order.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;

import com.fourman.common.mapper.EntityMapper;
import com.fourman.order.domain.OrderItem;
import com.fourman.order.infrastructure.persistence.entity.OrderItemEntity;

@Mapper(componentModel = "spring")
public interface OrderItemEntityMapper extends EntityMapper<OrderItem, OrderItemEntity> {}
