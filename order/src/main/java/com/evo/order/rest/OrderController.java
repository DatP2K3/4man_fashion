package com.evo.order.rest;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.evo.common.dto.request.SearchOrderRequest;
import com.evo.common.dto.response.ApiResponses;
import com.evo.common.dto.response.OrderDTO;
import com.evo.common.dto.response.PageApiResponse;
import com.evo.order.application.dto.request.*;
import com.evo.order.application.dto.response.OrderFeeDTO;
import com.evo.order.application.service.OrderCommandService;
import com.evo.order.application.service.OrderQueryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OrderController {
    private final OrderQueryService orderQueryService;
    private final OrderCommandService orderCommandService;

    @PreAuthorize("hasRole('USER')")
    @GetMapping("caculate-fee/{toAddressId}")
    public ApiResponses<OrderFeeDTO> caculateFeeByAddressId(@PathVariable UUID toAddressId) {
        OrderFeeDTO orderFeeDTO = orderQueryService.caculateFeeByAddressId(toAddressId);
        return ApiResponses.<OrderFeeDTO>builder()
                .data(orderFeeDTO)
                .success(true)
                .code(200)
                .message("Get order fee successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/orders")
    ApiResponses<Void> deleteOrder(@RequestBody CancelOrderRequest cancelOrderRequest) {
        orderCommandService.delete(cancelOrderRequest);
        return ApiResponses.<Void>builder()
                .success(true)
                .code(200)
                .message("Delete order successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/orders")
    public ApiResponses<List<OrderDTO>> getOrdersOfUser() {
        List<OrderDTO> orderDTOs = orderQueryService.getOrdersOfUser();
        return ApiResponses.<List<OrderDTO>>builder()
                .data(orderDTOs)
                .success(true)
                .code(200)
                .message("Get orders successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("orders/search")
    public PageApiResponse<List<OrderDTO>> searchOrders(SearchOrderRequest request) {
        Long totalOrders = orderQueryService.count(request);
        List<OrderDTO> orderDTOs = Collections.emptyList();
        if (totalOrders != 0) {
            orderDTOs = orderQueryService.search(request);
        }
        PageApiResponse.PageableResponse pageableResponse = PageApiResponse.PageableResponse.builder()
                .pageIndex(request.getPageIndex())
                .pageSize(request.getPageSize())
                .totalPages((int) (Math.ceil((double) totalOrders / request.getPageSize())))
                .hasNext(request.getPageIndex() * request.getPageSize() < totalOrders)
                .hasPrevious(request.getPageIndex() > 1)
                .totalElements(totalOrders)
                .build();
        return PageApiResponse.<List<OrderDTO>>builder()
                .data(orderDTOs)
                .success(true)
                .code(200)
                .pageable(pageableResponse)
                .message("Search orders successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/orders")
    public ApiResponses<OrderDTO> createOrder(@RequestBody CreateOrderRequest request) {
        OrderDTO orderDTO = orderCommandService.create(request);
        return ApiResponses.<OrderDTO>builder()
                .data(orderDTO)
                .success(true)
                .code(200)
                .message("Create order successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/orders/{orderCode}")
    public ApiResponses<OrderDTO> getOrderByOrderCode(@PathVariable String orderCode) {
        OrderDTO orderDTO = orderQueryService.findByOrderCode(orderCode);
        return ApiResponses.<OrderDTO>builder()
                .data(orderDTO)
                .success(true)
                .code(200)
                .message("Get order successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/orders/ghn")
    public ApiResponses<List<OrderDTO>> createGHNOrder(@RequestBody CreatShippingOrderRequest request) {
        List<OrderDTO> ghnOrders = orderCommandService.createGHNOrder(request);
        return ApiResponses.<List<OrderDTO>>builder()
                .data(ghnOrders)
                .success(true)
                .code(200)
                .message("Create GHN orders successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/orders/ghn-order/print")
    public String printGHNOrder(@RequestBody PrintOrCancelGHNOrderRequest request) {
        orderCommandService.printGHNOrder(request.getOrderCodes());
        return orderQueryService.printGHNOrder(request);
    }
}
