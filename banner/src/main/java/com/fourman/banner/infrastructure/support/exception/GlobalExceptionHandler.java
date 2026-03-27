package com.fourman.banner.infrastructure.support.exception;

import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fourman.common.webapp.exception.CommonGlobalExceptionHandler;
import com.fourman.common.webapp.i18n.MessageHelper;

/**
 * Banner-specific exception handler.
 * Inherits all i18n error handling from CommonGlobalExceptionHandler.
 * Add banner-specific handlers here if needed.
 */
@RestControllerAdvice
@Order(1)
public class GlobalExceptionHandler extends CommonGlobalExceptionHandler {
    public GlobalExceptionHandler(MessageHelper messageHelper) {
        super(messageHelper);
    }
}
