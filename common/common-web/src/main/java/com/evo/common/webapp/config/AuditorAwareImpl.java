package com.evo.common.webapp.config;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.AuditorAware;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuditorAwareImpl implements AuditorAware<UUID> {
    @Value("${auth.keycloak-enabled}")
    private boolean keycloakEnabled;

    @Override
    public @NonNull Optional<UUID> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null
                || !authentication.isAuthenticated()
                || authentication.getPrincipal().equals("anonymousUser")) {
            return Optional.empty();
        }
        return Optional.of(UUID.fromString(authentication.getName()));
    }
}
