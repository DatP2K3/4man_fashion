package com.fourman.order.application.mapper;

import org.mapstruct.Mapper;

import com.fourman.common.dto.event.OrderEvent;
import com.fourman.order.application.dto.request.CreateOrderItemRequest;
import com.fourman.order.application.dto.request.CreateOrderRequest;
import com.fourman.order.domain.command.CreateOrderCmd;
import com.fourman.order.domain.command.UpdateOrderStatusCmd;

@Mapper(componentModel = "spring")
public interface CommandMapper {
    CreateOrderCmd from(CreateOrderRequest request);

    CreateOrderRequest from(CreateOrderItemRequest request);

    UpdateOrderStatusCmd from(OrderEvent event);
}
