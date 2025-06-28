package com.evo.profile.infrastructure.adapter.keycloak;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.evo.common.dto.response.TokenDTO;
import com.evo.profile.application.dto.request.GetTokenRequest;
import com.evo.profile.application.dto.request.LockUserRequest;
import com.evo.profile.application.dto.request.ResetKeycloakPasswordRequest;
import com.evo.profile.infrastructure.adapter.keycloak.config.KeycloakIdentityClientConfiguration;

import feign.QueryMap;

@FeignClient(
        name = "identity-client",
        url = "${idp.url}",
        contextId = "identity-client",
        configuration = KeycloakIdentityClientConfiguration.class)
public interface KeycloakIdentityClient {
    @PostMapping(
            value = "/realms/IamService/protocol/openid-connect/token",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    TokenDTO getToken(@QueryMap GetTokenRequest param);

    @PutMapping(value = "/admin/realms/IamService/users/{user_id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    void lockUser(
            @RequestHeader("authorization") String token,
            @PathVariable("user_id") String userId,
            @RequestBody LockUserRequest lockUserRequest);

    @PutMapping(
            value = "/admin/realms/IamService/users/{user_id}/reset-password",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    void resetPassword(
            @RequestHeader("authorization") String token,
            @PathVariable("user_id") String userId,
            @RequestBody ResetKeycloakPasswordRequest resetKeycloakPasswordRequest);
}
