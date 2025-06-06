package com.evo.order.infrastructure.adapter.cart.client;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import com.evo.common.dto.response.ApiResponses;
import com.evo.common.dto.response.CartDTO;
import com.evo.order.infrastructure.adapter.cart.config.FeignCartClientConfiguration;

@FeignClient(
        name = "cart-service",
        url = "${app.cart-service.url:}",
        contextId = "cart-with-token",
        configuration = FeignCartClientConfiguration.class,
        fallbackFactory = CartClientFallback.class)
public interface CartClient {
    @GetMapping("/api/carts/get-or-init")
    ApiResponses<CartDTO> getCart();

    @PutMapping("/api/carts/empty/{cartId}")
    ApiResponses<Void> emptyCart(@PathVariable("cartId") UUID cartId);
}
