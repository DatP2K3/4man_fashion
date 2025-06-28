package com.evo.order.infrastructure.adapter.shopinfo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShopInfoClientConfiguration {
    @Bean
    public ShopInfoClientInterceptor requestInterceptor() {
        return new ShopInfoClientInterceptor();
    }
}
