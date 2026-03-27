package com.fourman.profile.infrastructure.adapter.keycloak;

import java.util.UUID;

import com.fourman.profile.application.dto.request.ResetKeycloakPasswordRequest;

public interface KeycloakService {

    void resetPassword(UUID userId, ResetKeycloakPasswordRequest resetKeycloakPasswordRequest);

    String getClientToken();

    void lockUser(UUID userId, boolean enabled);
}
