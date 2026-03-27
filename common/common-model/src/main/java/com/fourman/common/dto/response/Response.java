package com.fourman.common.dto.response;

import java.io.Serializable;
import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fourman.common.exception.ResponseException;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Unified API response wrapper.
 * Success: Response.of(data) or Response.ok()
 * Fail: Response.fail(exception) — used only in Feign fallbacks.
 * Error responses from controllers are handled by CommonGlobalExceptionHandler.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Response<T> implements Serializable {
    protected T data;

    @Builder.Default
    private boolean success = true;

    @Builder.Default
    private int code = 200;

    private String message;

    @Builder.Default
    private long timestamp = Instant.now().toEpochMilli();

    @Builder.Default
    private String status = "SUCCESS";

    @JsonIgnore
    private transient RuntimeException exception;

    // ===== Factory methods =====

    public static <T> Response<T> of(T data) {
        Response<T> response = new Response<>();
        response.data = data;
        response.success = true;
        response.code = 200;
        response.status = "SUCCESS";
        response.timestamp = Instant.now().toEpochMilli();
        return response;
    }

    public static <T> Response<T> ok() {
        Response<T> response = new Response<>();
        response.success = true;
        response.code = 200;
        response.status = "SUCCESS";
        response.timestamp = Instant.now().toEpochMilli();
        return response;
    }

    public static <T> Response<T> fail(RuntimeException exception) {
        Response<T> response = new Response<>();
        response.success = false;
        response.code = 500;
        response.status = "FAIL";
        response.exception = exception;
        response.timestamp = Instant.now().toEpochMilli();
        if (exception instanceof ResponseException re) {
            response.code = re.getError().getCode();
            response.message = re.getError().getMessage();
        } else {
            response.message = exception.getMessage();
        }
        return response;
    }

    /**
     * Mark this response as success.
     */
    public Response<T> success() {
        this.success = true;
        this.code = 200;
        this.status = "SUCCESS";
        return this;
    }

    public T getData() {
        if (this.exception != null) {
            throw this.exception;
        }
        return this.data;
    }
}
