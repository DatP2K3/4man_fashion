package com.fourman.order.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.fourman.common.mapper.EntityMapper;
import com.fourman.order.domain.OrderItem;
import com.fourman.order.infrastructure.persistence.entity.OrderItemEntity;

@Mapper(componentModel = "spring")
public interface OrderItemEntityMapper extends EntityMapper<OrderItem, OrderItemEntity> {
    @Override
    @Mapping(target = "name", ignore = true)
    OrderItemEntity toEntity(OrderItem domain);

    @Override
    @Mapping(target = "weight", ignore = true)
    @Mapping(target = "height", ignore = true)
    @Mapping(target = "width", ignore = true)
    @Mapping(target = "length", ignore = true)
    OrderItem toDomainModel(OrderItemEntity entity);
}
