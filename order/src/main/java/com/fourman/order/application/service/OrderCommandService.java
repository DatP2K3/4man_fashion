package com.fourman.order.application.service;

import java.util.List;

import com.fourman.common.dto.response.OrderDTO;
import com.fourman.order.application.dto.request.CancelOrderRequest;
import com.fourman.order.application.dto.request.CreatShippingOrderRequest;
import com.fourman.order.application.dto.request.CreateOrderRequest;
import com.fourman.order.domain.command.UpdateOrderStatusCmd;

public interface OrderCommandService {
    OrderDTO create(CreateOrderRequest request);

    void delete(CancelOrderRequest cancelOrderRequest);

    List<OrderDTO> createGHNOrder(CreatShippingOrderRequest request);

    void updateStatus(UpdateOrderStatusCmd updateOrderStatusCmd);

    void printGHNOrder(List<String> orderCodes);
}
