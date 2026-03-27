package com.fourman.order.application.service;

import java.util.List;
import java.util.UUID;

import com.fourman.common.dto.request.SearchOrderRequest;
import com.fourman.common.dto.response.OrderDTO;
import com.fourman.common.dto.response.PageDTO;
import com.fourman.order.application.dto.request.PrintOrCancelGHNOrderRequest;
import com.fourman.order.application.dto.response.OrderFeeDTO;
import com.fourman.order.domain.query.SearchOrderQuery;

public interface OrderQueryService {
    PageDTO<OrderDTO> search(SearchOrderRequest request);

    Long count(SearchOrderQuery query);

    OrderFeeDTO calculateFeeByAddressId(UUID toAddressId);

    OrderDTO findByOrderCode(String orderCode);

    String printGHNOrder(PrintOrCancelGHNOrderRequest getPrintTokenRequest);

    String getGHNPrintToken(PrintOrCancelGHNOrderRequest getPrintTokenRequest);

    List<OrderDTO> getOrdersOfUser();
}
