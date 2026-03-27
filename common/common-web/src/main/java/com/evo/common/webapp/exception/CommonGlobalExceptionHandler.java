package com.evo.common.webapp.exception;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.evo.common.exception.ResponseException;
import com.evo.common.webapp.i18n.MessageHelper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Centralized exception handler with i18n support.
 * Resolves error messages based on Accept-Language header (en, vi).
 */
@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class CommonGlobalExceptionHandler {
    private final MessageHelper messageHelper;

    /**
     * Handle business exceptions (ResponseException) with i18n.
     */
    @ExceptionHandler(ResponseException.class)
    public ResponseEntity<Map<String, Object>> handleResponseException(ResponseException ex) {
        String localizedMessage = messageHelper.getMessage(ex.getError().getMessageKey(), ex.getParams());

        Map<String, Object> body = new HashMap<>();
        body.put("success", false);
        body.put("code", ex.getError().getCode());
        body.put("status", ex.getError().getName());
        body.put("message", localizedMessage);
        body.put("timestamp", Instant.now().toEpochMilli());

        log.error("Business error: {} - {}", ex.getError().getName(), localizedMessage);
        return new ResponseEntity<>(body, HttpStatus.valueOf(ex.getError().getStatus()));
    }

    /**
     * Handle @Valid validation errors with i18n.
     * Each field error is translated based on the validation annotation.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(MethodArgumentNotValidException ex) {
        List<Map<String, String>> errors = new ArrayList<>();

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            Map<String, String> errorDetail = new HashMap<>();
            errorDetail.put("field", fieldError.getField());

            // Try to resolve i18n key based on validation annotation
            String messageKey = resolveValidationMessageKey(fieldError);
            String localizedMessage = messageHelper.getMessage(messageKey, fieldError.getField());

            errorDetail.put("message", localizedMessage);
            errors.add(errorDetail);
        }

        Map<String, Object> body = new HashMap<>();
        body.put("success", false);
        body.put("code", 400);
        body.put("status", "VALIDATION_FAILED");
        body.put("message", messageHelper.getMessage("error.validation_failed"));
        body.put("errors", errors);
        body.put("timestamp", Instant.now().toEpochMilli());

        log.warn("Validation failed: {} errors", errors.size());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle all other unexpected exceptions.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        String localizedMessage = messageHelper.getMessage("error.internal_server");

        Map<String, Object> body = new HashMap<>();
        body.put("success", false);
        body.put("code", 500);
        body.put("status", "INTERNAL_SERVER_ERROR");
        body.put("message", localizedMessage);
        body.put("timestamp", Instant.now().toEpochMilli());

        log.error("Unexpected error: ", ex);
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Maps validation annotation to i18n message key.
     */
    private String resolveValidationMessageKey(FieldError fieldError) {
        String code = fieldError.getCode();
        if (code == null) return "error.bad_request";

        return switch (code) {
            case "NotNull" -> "validation.not_null";
            case "NotBlank" -> "validation.not_blank";
            case "NotEmpty" -> "validation.not_empty";
            case "Size" -> "validation.size";
            case "Min" -> "validation.min";
            case "Max" -> "validation.max";
            case "Email" -> "validation.email";
            case "Pattern" -> "validation.pattern";
            default -> "error.bad_request";
        };
    }
}
