package com.evo.order.infrastructure.persistence.mapper;

import com.evo.common.mapper.EntityMapper;
import com.evo.order.domain.OrderItem;
import com.evo.order.infrastructure.persistence.entity.OrderItemEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderItemEntityMapper extends EntityMapper<OrderItem, OrderItemEntity> {
}
