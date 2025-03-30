package com.evotek.iam.application.service;

import java.util.Map;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthServiceFactory {
    private final Map<String, AuthServiceCommand> authServiceCommand;
    private final Map<String, AuthServiceQuery> authServiceQuery;

    public AuthServiceCommand createAuthServiceCommand(String type) {
        return authServiceCommand.get(type);
    }

    public AuthServiceQuery createAuthServiceQuery(String type) {
        return authServiceQuery.get(type);
    }
}