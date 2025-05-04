package com.evo.order.infrastructure.adapter.ghn.client;

import com.evo.common.dto.response.ApiResponses;
import com.evo.order.application.dto.request.GetGHNFeeRequest;
import com.evo.order.application.dto.response.GHNFeeDTO;
import com.evo.order.application.dto.response.OrderFeeDTO;
import com.evo.order.infrastructure.adapter.ghn.config.FeignGHNClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "ghn-service",
        url = "${app.ghn.url:}",
        contextId = "ghn-with-token",
        configuration = FeignGHNClientConfiguration.class,
        fallbackFactory = GHNClientFallback.class)
public interface GHNClient {
    @PostMapping(value = "/shipping-order/fee",  consumes = MediaType.APPLICATION_JSON_VALUE)
    ApiResponses<GHNFeeDTO> calculateShippingFee(@RequestBody GetGHNFeeRequest request);
}
