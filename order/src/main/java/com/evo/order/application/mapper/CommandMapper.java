package com.evo.order.application.mapper;

import com.evo.order.application.dto.request.CreateOrderItemRequest;
import com.evo.order.application.dto.request.CreateOrderRequest;
import com.evo.order.domain.command.CreateOrderCmd;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommandMapper {
    CreateOrderCmd from(CreateOrderRequest request);
    CreateOrderRequest from(CreateOrderItemRequest request);
}
