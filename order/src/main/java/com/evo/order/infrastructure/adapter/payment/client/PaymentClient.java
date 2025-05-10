package com.evo.order.infrastructure.adapter.payment.client;

import com.evo.common.dto.request.GetPaymentUrlRequest;
import com.evo.common.dto.response.ApiResponses;
import com.evo.order.infrastructure.adapter.payment.config.FeignPaymentClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "payment-service",
        url = "${app.payment-service.url:}",
        contextId = "payment-with-token",
        configuration = FeignPaymentClientConfiguration.class,
        fallbackFactory = PaymentClientFallback.class)
public interface PaymentClient {
    @PostMapping(value = "/api/payment")
    ApiResponses<String> getPaymentUrl(@RequestBody GetPaymentUrlRequest getPaymentUrlRequest);
}
