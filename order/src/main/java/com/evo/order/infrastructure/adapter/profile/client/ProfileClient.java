package com.evo.order.infrastructure.adapter.profile.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import com.evo.common.dto.response.ApiResponses;
import com.evo.common.dto.response.ProfileDTO;
import com.evo.order.infrastructure.adapter.profile.config.FeignProfileClientConfiguration;

@FeignClient(
        name = "cart-service",
        url = "${app.profile-service.url:}",
        contextId = "cart-with-token",
        configuration = FeignProfileClientConfiguration.class,
        fallbackFactory = ProfileClientFallback.class)
public interface ProfileClient {
    @PostMapping("/api/profiles")
    ApiResponses<ProfileDTO> getProfile();
}
