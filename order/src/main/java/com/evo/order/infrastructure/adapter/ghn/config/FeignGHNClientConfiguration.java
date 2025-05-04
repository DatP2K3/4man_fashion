package com.evo.order.infrastructure.adapter.ghn.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignGHNClientConfiguration {
    @Bean
    public FeignGHNClientInterceptor requestInterceptor() {
        return new FeignGHNClientInterceptor();
    }
}
