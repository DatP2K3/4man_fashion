package com.evo.order.application.dto.mapper;

import com.evo.common.dto.response.DTOMapper;
import com.evo.order.application.dto.response.OrderDTO;
import com.evo.order.domain.Order;
import com.evo.order.domain.OrderItem;
import com.evo.order.infrastructure.persistence.entity.OrderEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {OrderItemDTOMapper.class})
public interface OrderDTOMapper extends DTOMapper<OrderDTO, Order, OrderEntity> {
}
