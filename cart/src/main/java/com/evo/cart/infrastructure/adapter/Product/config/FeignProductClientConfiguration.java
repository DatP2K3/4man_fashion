package com.evo.cart.infrastructure.adapter.Product.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignProductClientConfiguration {
    @Bean
    public FeignProductClientInterceptor requestInterceptor() {
        return new FeignProductClientInterceptor();
    }
}
