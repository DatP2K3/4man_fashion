package com.evo.order.rest;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.*;

import com.evo.common.dto.request.SearchOrderRequest;
import com.evo.common.dto.response.Response;
import com.evo.common.dto.response.OrderDTO;
import com.evo.common.dto.response.PagingResponse;
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
    public PagingResponse<List<OrderDTO>> searchOrders(SearchOrderRequest request) {
        Long totalOrders = this.orderQueryService.count(request);
        List<OrderDTO> orderDTOs = Collections.emptyList();
        if (totalOrders != 0) {
            orderDTOs = this.orderQueryService.search(request);
        }
        PagingResponse.PageableResponse pageableResponse = PagingResponse.PageableResponse.builder()
                .pageIndex(request.getPageIndex())
                .pageSize(request.getPageSize())
                .totalPages((int) (Math.ceil((double) totalOrders / request.getPageSize())))
                .hasNext(request.getPageIndex() * request.getPageSize() < totalOrders)
                .hasPrevious(request.getPageIndex() > 1)
                .totalElements(totalOrders)
                .build();
        return PagingResponse.<List<OrderDTO>>builder()
                .data(orderDTOs)
                .success(true)
                .code(200)
                .pageable(pageableResponse)
                .message("Search orders successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
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
