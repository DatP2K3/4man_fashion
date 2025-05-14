package com.evo.order.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;

import com.evo.common.mapper.EntityMapper;
import com.evo.order.domain.Order;
import com.evo.order.infrastructure.persistence.entity.OrderEntity;

@Mapper(componentModel = "spring")
public interface OrderEntityMapper extends EntityMapper<Order, OrderEntity> {}
