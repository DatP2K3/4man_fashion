package com.evo.order.application.service;

import com.evo.order.application.dto.response.OrderDTO;
import com.evo.order.application.dto.response.OrderFeeDTO;

import java.util.List;
import java.util.UUID;

public interface OrderQueryService {
    List<OrderDTO> findByUserId(UUID userId);

    OrderFeeDTO caculateFeeByAddressId(UUID toAddressId);
}
