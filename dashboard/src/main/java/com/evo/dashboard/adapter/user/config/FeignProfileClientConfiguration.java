package com.evo.dashboard.adapter.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignProfileClientConfiguration {
    @Bean
    public FeignProfileClientInterceptor requestInterceptor() {
        return new FeignProfileClientInterceptor();
    }
}
