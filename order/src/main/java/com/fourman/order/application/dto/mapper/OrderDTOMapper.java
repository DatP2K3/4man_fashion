package com.fourman.order.application.dto.mapper;

import org.mapstruct.Mapper;

import com.fourman.common.dto.response.DTOMapper;
import com.fourman.common.dto.response.OrderDTO;
import com.fourman.order.domain.Order;
import com.fourman.order.infrastructure.persistence.entity.OrderEntity;

@Mapper(
        componentModel = "spring",
        uses = {OrderItemDTOMapper.class})
public interface OrderDTOMapper extends DTOMapper<OrderDTO, Order, OrderEntity> {}
