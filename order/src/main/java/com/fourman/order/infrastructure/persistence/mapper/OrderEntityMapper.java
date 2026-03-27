package com.fourman.order.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;

import com.fourman.common.mapper.EntityMapper;
import com.fourman.order.domain.Order;
import com.fourman.order.infrastructure.persistence.entity.OrderEntity;

@Mapper(componentModel = "spring")
public interface OrderEntityMapper extends EntityMapper<Order, OrderEntity> {}
