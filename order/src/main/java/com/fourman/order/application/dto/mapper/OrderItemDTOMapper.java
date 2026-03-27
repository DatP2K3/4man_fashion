package com.fourman.order.application.dto.mapper;

import org.mapstruct.Mapper;

import com.fourman.common.dto.response.DTOMapper;
import com.fourman.common.dto.response.OrderItemDTO;
import com.fourman.order.domain.OrderItem;
import com.fourman.order.infrastructure.persistence.entity.OrderItemEntity;

@Mapper(componentModel = "spring")
public interface OrderItemDTOMapper extends DTOMapper<OrderItemDTO, OrderItem, OrderItemEntity> {}
