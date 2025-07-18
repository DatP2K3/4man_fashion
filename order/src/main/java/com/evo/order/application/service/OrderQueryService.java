package com.evo.order.application.service;

import java.util.List;
import java.util.UUID;

import com.evo.common.dto.request.SearchOrderRequest;
import com.evo.common.dto.response.OrderDTO;
import com.evo.common.dto.response.PageDTO;
import com.evo.order.application.dto.request.PrintOrCancelGHNOrderRequest;
import com.evo.order.application.dto.response.OrderFeeDTO;
import com.evo.order.domain.query.SearchOrderQuery;

public interface OrderQueryService {
    PageDTO<OrderDTO> search(SearchOrderRequest request);

    Long count(SearchOrderQuery query);

    OrderFeeDTO caculateFeeByAddressId(UUID toAddressId);

    OrderDTO findByOrderCode(String orderCode);

    String printGHNOrder(PrintOrCancelGHNOrderRequest getPrintTokenRequest);

    String getGHYNPrintToken(PrintOrCancelGHNOrderRequest getPrintTokenRequest);

    List<OrderDTO> getOrdersOfUser();
}
