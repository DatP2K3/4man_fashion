package com.evo.gateway.config;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@RequiredArgsConstructor
public class ForbiddenTokenFilter implements WebFilter {
    private final TokenCacheService tokenCacheService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return exchange.getPrincipal()
                .filter(principal -> principal instanceof JwtAuthenticationToken)
                .cast(JwtAuthenticationToken.class)
                .flatMap(authentication -> {
                    Jwt token = authentication.getToken();
                    if (tokenCacheService.isExisted(TokenCacheService.INVALID_TOKEN_CACHE, token.getTokenValue())
                            || tokenCacheService.isExisted(
                                    TokenCacheService.INVALID_REFRESH_TOKEN_CACHE, token.getTokenValue())) {
                        log.warn("Token is invalid");
                        exchange.getResponse().setStatusCode(org.springframework.http.HttpStatus.UNAUTHORIZED);
                        return exchange.getResponse()
                                .writeWith(Mono.just(
                                        exchange.getResponse().bufferFactory().wrap("Token is invalid".getBytes())));
                    }
                    return chain.filter(exchange);
                })
                .switchIfEmpty(chain.filter(exchange));
    }
}
