package com.evo.profile.infrastructure.adapter.keycloak;

import java.util.UUID;

import com.evo.profile.application.dto.request.ResetKeycloakPasswordRequest;

public interface KeycloakService {

    void resetPassword(UUID userId, ResetKeycloakPasswordRequest resetKeycloakPasswordRequest);

    String getClientToken();

    void lockUser(UUID userId, boolean enabled);
}
