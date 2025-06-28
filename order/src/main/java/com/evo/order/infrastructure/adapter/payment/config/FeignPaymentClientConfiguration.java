package com.evo.order.infrastructure.adapter.payment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignPaymentClientConfiguration {
    @Bean
    public FeignPaymentClientInterceptor requestInterceptor() {
        return new FeignPaymentClientInterceptor();
    }
}
