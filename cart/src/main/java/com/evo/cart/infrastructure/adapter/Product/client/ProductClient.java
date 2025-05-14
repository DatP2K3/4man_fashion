package com.evo.cart.infrastructure.adapter.Product.client;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import com.evo.cart.infrastructure.adapter.Product.config.FeignProductClientConfiguration;
import com.evo.common.dto.response.ApiResponses;
import com.evo.common.dto.response.ProductDTO;

@FeignClient(
        name = "product-service",
        url = "${app.product-service.url:}",
        contextId = "product-with-token",
        configuration = FeignProductClientConfiguration.class,
        fallbackFactory = ProductClientFallback.class)
public interface ProductClient {
    @GetMapping("/api/products/{productId}")
    ApiResponses<ProductDTO> getProduct(@PathVariable("productId") UUID productId);
}
