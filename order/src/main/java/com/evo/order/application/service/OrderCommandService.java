package com.evo.order.application.service;

import com.evo.common.enums.OrderStatus;
import com.evo.order.application.dto.request.CancelOrderRequest;
import com.evo.order.application.dto.request.CreatShippingOrderRequest;
import com.evo.order.application.dto.request.CreateOrderRequest;
import com.evo.order.application.dto.response.GHNOrderDTO;
import com.evo.order.application.dto.response.OrderDTO;
import com.evo.order.domain.Order;

import java.util.List;
import java.util.UUID;

public interface OrderCommandService {
    OrderDTO create(CreateOrderRequest request);
    void delete(CancelOrderRequest  cancelOrderRequest);
    List<OrderDTO> createGHNOrder(CreatShippingOrderRequest request);
}
