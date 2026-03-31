package com.fourman.common.webapp.config.inbound;

import java.lang.reflect.Method;
import java.time.Duration;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

import jakarta.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fourman.common.webapp.support.ClientInfo;
import com.fourman.common.webapp.support.ClientInfoUtils;
import com.fourman.common.webapp.support.StringPool;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@ConditionalOnBean(RequestLogRepository.class)
@RequiredArgsConstructor
@Slf4j
public class InboundRequestAspect {

    private final RequestLogRepository requestLogRepository;
    private final ObjectMapper objectMapper;

    @Pointcut("@within(com.fourman.common.webapp.config.inbound.InboundRequest)")
    void classLevel() {}

    @Pointcut("@annotation(com.fourman.common.webapp.config.inbound.InboundRequest)")
    void methodLevel() {}

    @Around("classLevel() || methodLevel()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest httpRequest = currentRequest();

        if (httpRequest != null && StringPool.GET.equalsIgnoreCase(httpRequest.getMethod())) {
            return joinPoint.proceed();
        }

        Instant requestAt = Instant.now();

        InboundRequest cfg = resolveAnnotation(joinPoint);
        boolean logReqBody = cfg.logRequestBody();
        boolean logResBody = cfg.logResponseBody();
        int maxLen = cfg.maxPayloadLength();

        ClientInfo client = (httpRequest != null) ? ClientInfoUtils.extract(httpRequest) : null;
        String method = (httpRequest != null) ? httpRequest.getMethod() : StringPool.UNKNOWN;
        String path = (httpRequest != null) ? httpRequest.getRequestURI() : StringPool.UNKNOWN;
        String queryString = (httpRequest != null) ? httpRequest.getQueryString() : null;

        String requestBody = null;
        if (logReqBody && httpRequest != null) {
            requestBody = serializeArgs(joinPoint.getArgs(), maxLen);
        }

        Object result = null;
        Integer status = 200;
        String responseBody = null;

        try {
            result = joinPoint.proceed();
            if (logResBody && result != null) {
                responseBody = serialize(result, maxLen);
            }
        } catch (Throwable ex) {
            status = 500;
            responseBody = truncate(sanitize(ex.getMessage()), maxLen);
            throw ex;
        } finally {
            Instant responseAt = Instant.now();
            long durationMs = Duration.between(requestAt, responseAt).toMillis();

            try {
                RequestLog logEntry = RequestLog.builder()
                        .method(method)
                        .path(path)
                        .queryString(queryString)
                        .clientIp(client != null ? client.ip() : null)
                        .clientOs(client != null ? client.os() : null)
                        .clientBrowser(client != null ? client.browser() : null)
                        .clientDevice(client != null ? client.device() : null)
                        .userAgent(client != null ? client.userAgent() : null)
                        .requestBody(requestBody)
                        .responseBody(responseBody)
                        .requestAt(requestAt)
                        .responseAt(responseAt)
                        .durationMs(durationMs)
                        .status(status)
                        .createdBy(extractCurrentUserId())
                        .build();

                saveAsync(logEntry);
            } catch (Exception e) {
                log.warn("InboundRequestAspect: failed to save request log", e);
            }
        }

        return result;
    }

    @Async
    void saveAsync(RequestLog logEntry) {
        try {
            requestLogRepository.save(logEntry);
        } catch (Exception e) {
            log.error("InboundRequestAspect: async save failed for path={}", logEntry.getPath(), e);
        }
    }

    private InboundRequest resolveAnnotation(ProceedingJoinPoint joinPoint) {
        MethodSignature sig = (MethodSignature) joinPoint.getSignature();
        Method method = sig.getMethod();

        InboundRequest methodAnnotation = AnnotationUtils.findAnnotation(method, InboundRequest.class);
        if (methodAnnotation != null) {
            return methodAnnotation;
        }
        InboundRequest classAnnotation =
                AnnotationUtils.findAnnotation(joinPoint.getTarget().getClass(), InboundRequest.class);
        if (classAnnotation != null) {
            return classAnnotation;
        }
        return DefaultInboundRequest.INSTANCE;
    }

    private String serializeArgs(Object[] args, int maxLen) {
        if (args == null || args.length == 0) return null;
        try {
            Object toSerialize = args.length == 1 ? args[0] : filterSerializableArgs(args);
            return serialize(toSerialize, maxLen);
        } catch (Exception e) {
            log.debug("InboundRequestAspect: could not serialize request args", e);
            return null;
        }
    }

    private Object filterSerializableArgs(Object[] args) {
        java.util.List<Object> filtered = new java.util.ArrayList<>();
        for (Object arg : args) {
            if (arg instanceof jakarta.servlet.ServletRequest
                    || arg instanceof jakarta.servlet.ServletResponse
                    || arg instanceof org.springframework.web.multipart.MultipartFile) {
                continue;
            }
            filtered.add(arg);
        }
        return filtered.size() == 1 ? filtered.getFirst() : filtered;
    }

    private String serialize(Object obj, int maxLen) {
        try {
            String json = objectMapper.writeValueAsString(obj);
            return truncate(sanitize(json), maxLen);
        } catch (Exception e) {
            log.debug("InboundRequestAspect: serialization error", e);
            return null;
        }
    }

    private static String sanitize(String text) {
        if (text == null) return null;
        return text.replaceAll(StringPool.SENSITIVE_FIELDS_REGEX, StringPool.SENSITIVE_REPLACEMENT);
    }

    private static String truncate(String text, int maxLen) {
        if (text == null || text.length() <= maxLen) return text;
        return text.substring(0, maxLen) + "...[TRUNCATED]";
    }

    private static HttpServletRequest currentRequest() {
        try {
            ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            return attrs != null ? attrs.getRequest() : null;
        } catch (Exception e) {
            return null;
        }
    }

    private static UUID extractCurrentUserId() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth == null || !auth.isAuthenticated() || Objects.equals(auth.getPrincipal(), "anonymousUser")) {
                return null;
            }
            return UUID.fromString(auth.getName());
        } catch (Exception e) {
            return null;
        }
    }

    private static final class DefaultInboundRequest {

        @InboundRequest
        private static void holder() {}

        static final InboundRequest INSTANCE;

        static {
            try {
                INSTANCE =
                        DefaultInboundRequest.class.getDeclaredMethod("holder").getAnnotation(InboundRequest.class);
            } catch (NoSuchMethodException e) {
                throw new ExceptionInInitializerError(e);
            }
        }
    }
}
