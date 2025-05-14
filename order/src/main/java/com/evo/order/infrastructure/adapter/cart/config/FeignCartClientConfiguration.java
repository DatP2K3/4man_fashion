package com.evo.order.infrastructure.adapter.cart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignCartClientConfiguration {
    @Bean
    public FeignCartClientInterceptor requestInterceptor() {
        return new FeignCartClientInterceptor();
    }
}
