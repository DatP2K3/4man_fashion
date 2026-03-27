package com.fourman.order.infrastructure.adapter.payment.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.fourman.common.dto.request.GetPaymentUrlRequest;
import com.fourman.common.dto.response.Response;
import com.fourman.order.infrastructure.adapter.payment.config.FeignPaymentClientConfiguration;

@FeignClient(
        name = "payment-service",
        url = "${app.payment-service.url:}",
        contextId = "payment-with-token",
        configuration = FeignPaymentClientConfiguration.class,
        fallbackFactory = PaymentClientFallback.class)
public interface PaymentClient {
    @PostMapping(value = "/api/payment")
    Response<String> getPaymentUrl(@RequestBody GetPaymentUrlRequest getPaymentUrlRequest);
}
