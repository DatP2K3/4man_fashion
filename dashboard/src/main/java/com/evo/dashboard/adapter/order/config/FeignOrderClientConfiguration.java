package com.evo.dashboard.adapter.order.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignOrderClientConfiguration {
    @Bean
    public FeignOrderClientInterceptor requestInterceptor() {
        return new FeignOrderClientInterceptor();
    }
}
