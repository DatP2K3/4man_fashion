package com.evo.order.infrastructure.adapter.shopinfo.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.evo.common.dto.response.ApiResponses;
import com.evo.common.dto.response.ShopAddressDTO;
import com.evo.order.infrastructure.adapter.shopinfo.config.ShopInfoClientConfiguration;

@FeignClient(
        name = "shopInfo-service",
        url = "${app.shopinfo-service.url:}",
        contextId = "shopInfo-with-token",
        configuration = ShopInfoClientConfiguration.class,
        fallbackFactory = ShopInfoClientFallback.class)
public interface ShopInfoClient {
    @GetMapping("/api/shop-address")
    ApiResponses<List<ShopAddressDTO>> getShopAddress();
}
