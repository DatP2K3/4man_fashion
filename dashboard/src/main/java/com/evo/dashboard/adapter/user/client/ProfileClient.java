package com.evo.dashboard.adapter.user.client;

import java.time.Instant;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.evo.common.dto.response.PageApiResponse;
import com.evo.common.dto.response.ProfileDTO;
import com.evo.dashboard.adapter.user.config.FeignProfileClientConfiguration;

@FeignClient(
        name = "profile-service",
        url = "${app.profile-service.url:}",
        contextId = "profile-with-token",
        configuration = FeignProfileClientConfiguration.class,
        fallbackFactory = ProfileClientFallback.class)
public interface ProfileClient {
    @GetMapping("api/all-profiles")
    PageApiResponse<List<ProfileDTO>> searchProfiles(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Instant createdFrom,
            @RequestParam(required = false) Instant createdTo);
}
