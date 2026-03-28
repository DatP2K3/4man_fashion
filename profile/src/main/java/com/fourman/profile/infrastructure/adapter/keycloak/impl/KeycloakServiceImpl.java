package com.fourman.profile.infrastructure.adapter.keycloak.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fourman.common.dto.response.TokenDTO;
import com.fourman.profile.application.dto.request.GetTokenRequest;
import com.fourman.profile.application.dto.request.LockUserRequest;
import com.fourman.profile.application.dto.request.ResetKeycloakPasswordRequest;
import com.fourman.profile.domain.exception.ErrorNormalizer;
import com.fourman.profile.infrastructure.adapter.keycloak.KeycloakIdentityClient;
import com.fourman.profile.infrastructure.adapter.keycloak.KeycloakService;

import feign.FeignException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KeycloakServiceImpl implements KeycloakService {
    private final KeycloakIdentityClient keycloakIdentityClient;
    private final ErrorNormalizer errorNormalizer;

    @Value("${idp.client-id}")
    private String clientId;

    @Value("${idp.client-secret}")
    private String clientSecret;

    @Override
    public void resetPassword(UUID userId, ResetKeycloakPasswordRequest resetKeycloakPasswordRequest) {
        String token = getClientToken();
        try {
            keycloakIdentityClient.resetPassword("Bearer " + token, userId.toString(), resetKeycloakPasswordRequest);
        } catch (FeignException e) {
            throw errorNormalizer.handleKeyCloakException(e);
        }
    }

    @Override
    public String getClientToken() {
        try {
            TokenDTO tokenDTO = keycloakIdentityClient.getToken(GetTokenRequest.builder()
                    .grant_type("client_credentials")
                    .client_id(clientId)
                    .client_secret(clientSecret)
                    .scope("openid")
                    .build());
            return tokenDTO.getAccessToken();
        } catch (FeignException e) {
            throw errorNormalizer.handleKeyCloakException(e);
        }
    }

    @Override
    public void lockUser(UUID userId, boolean enabled) {
        String token = getClientToken();
        LockUserRequest lockUserRequest =
                LockUserRequest.builder().enabled(enabled).build();
        try {
            keycloakIdentityClient.lockUser("Bearer " + token, userId.toString(), lockUserRequest);
        } catch (FeignException e) {
            throw errorNormalizer.handleKeyCloakException(e);
        }
    }
}
