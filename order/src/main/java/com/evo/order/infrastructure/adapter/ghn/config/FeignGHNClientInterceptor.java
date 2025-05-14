package com.evo.order.infrastructure.adapter.ghn.config;

import org.springframework.beans.factory.annotation.Value;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FeignGHNClientInterceptor implements RequestInterceptor {
    @Value("${app.ghn.id}")
    private String ghnId;

    @Value("${app.ghn.token}")
    private String ghnToken;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header("Token", ghnToken);
        requestTemplate.header("ShopId", ghnId);
    }
}
