package com.evo.order.application.mapper;

import org.mapstruct.Mapper;

import com.evo.common.dto.event.OrderEvent;
import com.evo.order.application.dto.request.CreateOrderItemRequest;
import com.evo.order.application.dto.request.CreateOrderRequest;
import com.evo.order.domain.command.CreateOrderCmd;
import com.evo.order.domain.command.UpdateOrderStatusCmd;

@Mapper(componentModel = "spring")
public interface CommandMapper {
    CreateOrderCmd from(CreateOrderRequest request);

    CreateOrderRequest from(CreateOrderItemRequest request);

    UpdateOrderStatusCmd from(OrderEvent event);
}
