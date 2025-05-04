package com.evo.order.infrastructure.adapter.cart.client;

import com.evo.common.dto.response.ApiResponses;
import com.evo.common.dto.response.CartDTO;
import com.evo.order.infrastructure.adapter.cart.config.FeignCartClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(
        name = "cart-service",
        url = "${app.cart-service.url:}",
        contextId = "cart-with-token",
        configuration = FeignCartClientConfiguration.class,
        fallbackFactory = CartClientFallback.class)
public interface CartClient {
    @GetMapping("/api/carts/get-or-init")
    ApiResponses<CartDTO> getCart();
}
