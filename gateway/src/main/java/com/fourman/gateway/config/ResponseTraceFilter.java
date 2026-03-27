package com.fourman.gateway.config;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class ResponseTraceFilter {
    private final FilterUtility filterUtility;

    @Bean
    public GlobalFilter postGlobalFilter() {
        return (exchange, chain) -> {
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                HttpHeaders requestHeaders = exchange.getRequest().getHeaders();
                String correlationId = filterUtility.getCorrelationId(requestHeaders);
                HttpStatusCode statusCode = exchange.getResponse().getStatusCode();

                // Gắn correlation ID vào response
                if (!exchange.getResponse().getHeaders().containsKey(FilterUtility.CORRELATION_ID)) {
                    exchange.getResponse().getHeaders().add(FilterUtility.CORRELATION_ID, correlationId);
                }

                // Log error responses
                if (statusCode != null && statusCode.isError()) {
                    String method = exchange.getRequest().getMethod().name();
                    String path = exchange.getRequest().getURI().getPath();
                    log.warn("[{}] {} {} → {} ERROR", correlationId, method, path, statusCode.value());
                }
            }));
        };
    }
}
