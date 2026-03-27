package com.fourman.dashboard.adapter.order.client;

import java.time.Instant;
import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fourman.common.dto.response.OrderDTO;
import com.fourman.common.dto.response.PagingResponse;
import com.fourman.common.enums.OrderStatus;
import com.fourman.dashboard.adapter.order.config.FeignOrderClientConfiguration;

@FeignClient(
        name = "order-service",
        url = "${app.order-service.url:}",
        contextId = "order-with-token",
        configuration = FeignOrderClientConfiguration.class,
        fallbackFactory = OrderClientFallback.class)
public interface OrderClient {
    @GetMapping("api/orders/search")
    PagingResponse<OrderDTO> searchOrders(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) UUID userId,
            @RequestParam(required = false) OrderStatus orderStatus,
            @RequestParam(required = false) Instant startDate,
            @RequestParam(required = false) Instant endDate,
            @RequestParam(required = false) Boolean printed,
            @RequestParam(defaultValue = "1") int pageIndex,
            @RequestParam(defaultValue = "30") int pageSize,
            @RequestParam(required = false) String sortBy);
}
