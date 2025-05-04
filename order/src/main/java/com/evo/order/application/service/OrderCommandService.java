package com.evo.order.application.service;

import com.evo.order.application.dto.request.CreateOrderRequest;
import com.evo.order.application.dto.response.OrderDTO;

public interface OrderCommandService {
    OrderDTO create(CreateOrderRequest request);
}
