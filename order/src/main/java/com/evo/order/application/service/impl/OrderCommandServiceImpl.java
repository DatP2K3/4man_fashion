package com.evo.order.application.service.impl;

import com.evo.order.application.dto.request.CreateOrderRequest;
import com.evo.order.application.dto.response.OrderDTO;
import com.evo.order.application.service.OrderCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderCommandServiceImpl implements OrderCommandService {
    @Override
    public OrderDTO create(CreateOrderRequest request) {
        return null;
    }
}
