package com.fourman.order.presentation.rest;

import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.fourman.common.dto.request.SearchOrderRequest;
import com.fourman.common.dto.response.OrderDTO;
import com.fourman.common.dto.response.PagingResponse;
import com.fourman.common.dto.response.Response;
import com.fourman.order.application.dto.request.*;
import com.fourman.order.application.dto.response.OrderFeeDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Order API")
@RequestMapping("/api")
@Validated
public interface OrderController {

    @Operation(summary = "Calculate fee by address")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("calculate-fee/{toAddressId}")
    Response<OrderFeeDTO> calculateFeeByAddressId(@PathVariable UUID toAddressId);

    @Operation(summary = "Delete order")
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/orders")
    Response<Void> deleteOrder(@Valid @RequestBody CancelOrderRequest cancelOrderRequest);

    @Operation(summary = "Get orders of user")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/orders")
    Response<List<OrderDTO>> getOrdersOfUser();

    @Operation(summary = "Search orders")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("orders/search")
    PagingResponse<OrderDTO> searchOrders(SearchOrderRequest request);

    @Operation(summary = "Create order")
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/orders")
    Response<OrderDTO> createOrder(@Valid @RequestBody CreateOrderRequest request);

    @Operation(summary = "Get order by order code")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/orders/{orderCode}")
    Response<OrderDTO> getOrderByOrderCode(@PathVariable String orderCode);

    @Operation(summary = "Create GHN order")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/orders/ghn")
    Response<List<OrderDTO>> createGHNOrder(@Valid @RequestBody CreatShippingOrderRequest request);

    @Operation(summary = "Print GHN order")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/orders/ghn-order/print")
    String printGHNOrder(@Valid @RequestBody PrintOrCancelGHNOrderRequest request);
}
