package com.evo.order.application.dto.mapper;

import org.mapstruct.Mapper;

import com.evo.common.dto.response.DTOMapper;
import com.evo.order.application.dto.response.OrderItemDTO;
import com.evo.order.domain.OrderItem;
import com.evo.order.infrastructure.persistence.entity.OrderItemEntity;

@Mapper(componentModel = "spring")
public interface OrderItemDTOMapper extends DTOMapper<OrderItemDTO, OrderItem, OrderItemEntity> {}
