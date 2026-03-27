package com.fourman.gateway.config;

import java.time.Duration;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * Redis-based rate limiter using sliding window counter.
 * Each IP gets a Redis key "rate_limit:{ip}" with TTL = WINDOW_DURATION.
 * Supports multiple gateway instances sharing the same Redis.
 */
@Order(-1)
@Component
@Slf4j
public class RateLimitFilter implements GlobalFilter {
    private static final int MAX_REQUESTS = 100;
    private static final Duration WINDOW_DURATION = Duration.ofMinutes(1);
    private static final String KEY_PREFIX = "rate_limit:";

    private final ReactiveStringRedisTemplate redisTemplate;

    public RateLimitFilter(ReactiveStringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String clientIp = getClientIp(exchange);
        String redisKey = KEY_PREFIX + clientIp;

        return redisTemplate.opsForValue().increment(redisKey).flatMap(count -> {
            Mono<Boolean> expireMono = (count == 1)
                    ? redisTemplate.expire(redisKey, WINDOW_DURATION)
                    : Mono.just(true);

            return expireMono.flatMap(result -> {
                long remaining = Math.max(0, MAX_REQUESTS - count);

                exchange.getResponse().getHeaders().add("X-RateLimit-Limit", String.valueOf(MAX_REQUESTS));
                exchange.getResponse().getHeaders().add("X-RateLimit-Remaining", String.valueOf(remaining));

                if (count > MAX_REQUESTS) {
                    log.warn("Rate limit exceeded for IP: {} ({} requests/min)", clientIp, count);
                    exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
                    exchange.getResponse().getHeaders().add("Retry-After", "60");
                    return exchange.getResponse().setComplete();
                }

                return chain.filter(exchange);
            });
        }).onErrorResume(ex -> {
            // Redis down → cho qua, không chặn user
            log.error("Rate limiter Redis error, allowing request through: {}", ex.getMessage());
            return chain.filter(exchange);
        });
    }

    private String getClientIp(ServerWebExchange exchange) {
        String xForwardedFor = exchange.getRequest().getHeaders().getFirst("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }
        var remoteAddress = exchange.getRequest().getRemoteAddress();
        return remoteAddress != null ? remoteAddress.getAddress().getHostAddress() : "unknown";
    }
}
