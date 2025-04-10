package com.evo.gateway.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoders;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import reactor.core.publisher.Mono;

import java.util.Arrays;

@Slf4j
@Configuration
@EnableWebFluxSecurity
public class HttpSecurityConfiguration {
    private final String[] PUBLIC_ENDPOINTS = {
            "/api/users",
            "/api/auth/login_iam",
            "/api/auth/verify-otp",
            "/api/auth/forgot-password",
            "/api/auth/reset-password",
            "/api/certificate/.well-known/jwks.json",
            "/users/authorities/**",
            "/api/public/**",
            "/api/client/authorities/**",
            "/api/client/token/**",
            "/api/uploads/**",
            "/actuator/**",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-config",
            "/swagger-ui.html",
            "favicon.ico",
            "/swagger-resources/**",
            "/webjars/**",
            "/swagger-resources/**",
            "/swagger-doc/**",
            "/api/test-retry",
            "api/test/refresh",
            "/api/config/reload",
            "/api/test/**",
            "/api/outbound/authentication"
    };

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers(PUBLIC_ENDPOINTS).permitAll()
                        .pathMatchers("/api/**").authenticated()
                        .anyExchange().permitAll()
                )
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> {}))
                .build();
    }

    @Bean
    public ReactiveJwtDecoder jwtDecoder() {
        return ReactiveJwtDecoders.fromIssuerLocation("http://localhost:8180/realms/IamService");
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
