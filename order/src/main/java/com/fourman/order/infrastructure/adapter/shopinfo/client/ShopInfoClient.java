package com.fourman.order.infrastructure.adapter.shopinfo.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.fourman.common.dto.response.Response;
import com.fourman.common.dto.response.ShopAddressDTO;
import com.fourman.order.infrastructure.adapter.shopinfo.config.ShopInfoClientConfiguration;

@FeignClient(
        name = "shopInfo-service",
        url = "${app.shopinfo-service.url:}",
        contextId = "shopInfo-with-token",
        configuration = ShopInfoClientConfiguration.class,
        fallbackFactory = ShopInfoClientFallback.class)
public interface ShopInfoClient {
    @GetMapping("/api/shop-address")
    Response<List<ShopAddressDTO>> getShopAddress();
}
