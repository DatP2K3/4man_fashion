package com.fourman.order.infrastructure.adapter.ghn.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.fourman.common.dto.response.Response;
import com.fourman.order.application.dto.request.CreateGHNOrderRequest;
import com.fourman.order.application.dto.request.GetGHNFeeRequest;
import com.fourman.order.application.dto.request.GetGHNOrderDetailRequest;
import com.fourman.order.application.dto.request.PrintOrCancelGHNOrderRequest;
import com.fourman.order.application.dto.response.GHNFeeDTO;
import com.fourman.order.application.dto.response.GHNOrderDTO;
import com.fourman.order.application.dto.response.GHNOrderDetailDTO;
import com.fourman.order.application.dto.response.GHNPrintTokenDTO;
import com.fourman.order.infrastructure.adapter.ghn.config.FeignGHNClientConfiguration;

@FeignClient(
        name = "ghn-service",
        url = "${app.ghn.url:}",
        contextId = "ghn-with-token",
        configuration = FeignGHNClientConfiguration.class,
        fallbackFactory = GHNClientFallback.class)
public interface GHNClient {
    @PostMapping(value = "/shiip/public-api/v2/shipping-order/fee", consumes = MediaType.APPLICATION_JSON_VALUE)
    Response<GHNFeeDTO> calculateShippingFee(@RequestBody GetGHNFeeRequest request);

    @PostMapping(value = "/shiip/public-api/v2/shipping-order/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    Response<GHNOrderDTO> createShippingOrder(@RequestBody CreateGHNOrderRequest request);

    @PostMapping(value = "/shiip/public-api/v2/a5/gen-token", consumes = MediaType.APPLICATION_JSON_VALUE)
    Response<GHNPrintTokenDTO> getPrintToken(@RequestBody PrintOrCancelGHNOrderRequest request);

    @GetMapping(value = "/a5/public-api/printA5", consumes = MediaType.APPLICATION_JSON_VALUE)
    Response<String> print(@RequestParam String token);

    @PostMapping(value = "/shiip/public-api/v2/switch-status/cancel", consumes = MediaType.APPLICATION_JSON_VALUE)
    Response<Void> cancelShippingOrder(@RequestBody PrintOrCancelGHNOrderRequest request);

    @PostMapping(value = "/shiip/public-api/v2/shipping-order/detail", consumes = MediaType.APPLICATION_JSON_VALUE)
    Response<GHNOrderDetailDTO> getOrderDetail(@RequestBody GetGHNOrderDetailRequest request);
}
