package com.evo.order.rest;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.*;

import com.evo.common.dto.request.SearchOrderRequest;
import com.evo.common.dto.response.OrderDTO;
import com.evo.common.dto.response.PagingResponse;
import com.evo.common.dto.response.Response;
import com.evo.order.application.dto.request.*;
import com.evo.order.application.dto.response.OrderFeeDTO;
import com.evo.order.application.service.OrderCommandService;
import com.evo.order.application.service.OrderQueryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OrderControllerImpl implements OrderController {
    private final OrderQueryService orderQueryService;
    private final OrderCommandService orderCommandService;

    @Override
    public Response<OrderFeeDTO> caculateFeeByAddressId(@PathVariable UUID toAddressId) {
        return Response.of(this.orderQueryService.caculateFeeByAddressId(toAddressId));
    }

    @Override
    public Response<Void> deleteOrder(@RequestBody CancelOrderRequest cancelOrderRequest) {
        this.orderCommandService.delete(cancelOrderRequest);
        return Response.ok();
    }

    @Override
    public Response<List<OrderDTO>> getOrdersOfUser() {
        return Response.of(this.orderQueryService.getOrdersOfUser());
    }

    @Override
    public PagingResponse<OrderDTO> searchOrders(SearchOrderRequest request) {
        return PagingResponse.of(this.orderQueryService.search(request));
    }

    @Override
    public Response<OrderDTO> createOrder(@RequestBody CreateOrderRequest request) {
        return Response.of(this.orderCommandService.create(request));
    }

    @Override
    public Response<OrderDTO> getOrderByOrderCode(@PathVariable String orderCode) {
        return Response.of(this.orderQueryService.findByOrderCode(orderCode));
    }

    @Override
    public Response<List<OrderDTO>> createGHNOrder(@RequestBody CreatShippingOrderRequest request) {
        return Response.of(this.orderCommandService.createGHNOrder(request));
    }

    @Override
    public String printGHNOrder(@RequestBody PrintOrCancelGHNOrderRequest request) {
        this.orderCommandService.printGHNOrder(request.getOrderCodes());
        return this.orderQueryService.printGHNOrder(request);
    }
}
