package com.fourman.publicinfo.config;

import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fourman.common.webapp.exception.CommonGlobalExceptionHandler;
import com.fourman.common.webapp.i18n.MessageHelper;

/**
 * Global exception handler for Public Info Service.
 * Inherits all i18n error handling from CommonGlobalExceptionHandler.
 */
@RestControllerAdvice
@Order(1)
public class GlobalExceptionHandler extends CommonGlobalExceptionHandler {
	public GlobalExceptionHandler(MessageHelper messageHelper) {
		super(messageHelper);
	}
}
