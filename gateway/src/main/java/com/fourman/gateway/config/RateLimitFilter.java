package com.fourman.gateway.config;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

/**
 * Simple in-memory rate limiter.
 * Limits each IP to MAX_REQUESTS per WINDOW_DURATION.
 * For production with multiple gateway instances, replace with Redis-based rate limiter.
 */
@Component
public class RateLimitFilter implements GlobalFilter, Ordered {
    private static final int MAX_REQUESTS = 100;
    private static final Duration WINDOW_DURATION = Duration.ofMinutes(1);

    private final Map<String, RateInfo> rateLimitMap = new ConcurrentHashMap<>();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String clientIp = getClientIp(exchange);

        RateInfo rateInfo = rateLimitMap.compute(clientIp, (key, existing) -> {
            if (existing == null || existing.isExpired()) {
                return new RateInfo();
            }
            return existing;
        });

        if (rateInfo.incrementAndCheck() > MAX_REQUESTS) {
            exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
            exchange.getResponse().getHeaders().add("X-RateLimit-Limit", String.valueOf(MAX_REQUESTS));
            exchange.getResponse().getHeaders().add("X-RateLimit-Remaining", "0");
            exchange.getResponse().getHeaders().add("Retry-After", "60");
            return exchange.getResponse().setComplete();
        }

        int remaining = MAX_REQUESTS - rateInfo.getCount();
        exchange.getResponse().getHeaders().add("X-RateLimit-Limit", String.valueOf(MAX_REQUESTS));
        exchange.getResponse().getHeaders().add("X-RateLimit-Remaining", String.valueOf(Math.max(0, remaining)));

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -1; // Run before other filters
    }

    private String getClientIp(ServerWebExchange exchange) {
        String xForwardedFor = exchange.getRequest().getHeaders().getFirst("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }
        var remoteAddress = exchange.getRequest().getRemoteAddress();
        return remoteAddress != null ? remoteAddress.getAddress().getHostAddress() : "unknown";
    }

    private static class RateInfo {
        private final Instant windowStart = Instant.now();
        private final AtomicInteger counter = new AtomicInteger(0);

        int incrementAndCheck() {
            return counter.incrementAndGet();
        }

        int getCount() {
            return counter.get();
        }

        boolean isExpired() {
            return Instant.now().isAfter(windowStart.plus(WINDOW_DURATION));
        }
    }
}
