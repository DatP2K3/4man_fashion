package com.evo.order.application.service;

import java.util.List;

import com.evo.order.application.dto.request.CancelOrderRequest;
import com.evo.order.application.dto.request.CreatShippingOrderRequest;
import com.evo.order.application.dto.request.CreateOrderRequest;
import com.evo.order.application.dto.response.OrderDTO;
import com.evo.order.domain.command.UpdateOrderStatusCmd;

public interface OrderCommandService {
    OrderDTO create(CreateOrderRequest request);

    void delete(CancelOrderRequest cancelOrderRequest);

    List<OrderDTO> createGHNOrder(CreatShippingOrderRequest request);

    void updateStatus(UpdateOrderStatusCmd updateOrderStatusCmd);
}
