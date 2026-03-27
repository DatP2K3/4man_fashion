package com.fourman.common.webapp.config;

import java.io.IOException;
import java.util.UUID;

import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Reads correlation ID from gateway header and puts it into SLF4J MDC.
 * Every log line in the service will automatically include the correlation ID.
 * If no correlation ID header exists (direct call), generates a new one.
 */
@Component
@Order(1)
public class CorrelationIdFilter implements Filter {
    public static final String CORRELATION_ID_HEADER = "4man-correlation-id";
    public static final String CORRELATION_ID_MDC_KEY = "correlationId";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Đọc correlation ID từ header (do Gateway gửi xuống)
        String correlationId = httpRequest.getHeader(CORRELATION_ID_HEADER);
        if (correlationId == null || correlationId.isBlank()) {
            // Gọi trực tiếp (không qua Gateway) → tạo mới
            correlationId = UUID.randomUUID().toString();
        }

        // Gắn vào MDC → tất cả log.info/warn/error tự động có correlationId
        MDC.put(CORRELATION_ID_MDC_KEY, correlationId);

        // Gắn vào response header → client nhận lại
        httpResponse.setHeader(CORRELATION_ID_HEADER, correlationId);

        try {
            chain.doFilter(request, response);
        } finally {
            // Dọn dẹp MDC sau khi request xong (tránh memory leak trong thread pool)
            MDC.remove(CORRELATION_ID_MDC_KEY);
        }
    }
}
