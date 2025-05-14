package com.evo.order.application.service;

import java.util.List;
import java.util.UUID;

import com.evo.order.application.dto.request.PrintOrCancelGHNOrderRequest;
import com.evo.order.application.dto.request.SearchOrderRequest;
import com.evo.order.application.dto.response.OrderDTO;
import com.evo.order.application.dto.response.OrderFeeDTO;

public interface OrderQueryService {
    List<OrderDTO> search(SearchOrderRequest request);

    Long count(SearchOrderRequest request);

    OrderFeeDTO caculateFeeByAddressId(UUID toAddressId);

    OrderDTO findByOrderCode(String orderCode);

    String printGHNOrder(PrintOrCancelGHNOrderRequest getPrintTokenRequest);

    String getGHYNPrintToken(PrintOrCancelGHNOrderRequest getPrintTokenRequest);

    List<OrderDTO> getOrdersOfUser();
}
