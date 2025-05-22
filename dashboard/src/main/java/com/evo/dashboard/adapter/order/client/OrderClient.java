package com.evo.dashboard.infrastructure.adapter.order.client;

import com.evo.common.dto.request.SearchOrderRequest;
import com.evo.common.dto.response.ApiResponses;
import com.evo.common.dto.response.CartDTO;
import com.evo.common.dto.response.OrderDTO;
import com.evo.dashboard.infrastructure.adapter.order.config.FeignOrderClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.UUID;

@FeignClient(
        name = "order-service",
        url = "${app.order-service.url:}",
        contextId = "cart-with-token",
        configuration = FeignOrderClientConfiguration.class,
        fallbackFactory = OrderClientFallback.class)
public interface OrderClient {
    @GetMapping("api/orders/search")
    ApiResponses<OrderDTO> searchOrders(SearchOrderRequest searchOrderRequest);
}
