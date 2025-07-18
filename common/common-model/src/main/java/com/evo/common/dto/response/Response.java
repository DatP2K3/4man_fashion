package com.evo.common.dto.response;

import java.io.Serializable;
import java.time.Instant;

import org.springframework.util.StringUtils;

import com.evo.common.enums.ErrorCodeClient;
import com.evo.common.exception.ResponseError;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@SuperBuilder
@Data
@AllArgsConstructor
public class Response<T> implements Serializable {
    protected T data;
    private boolean success = true;
    private int code = 200;
    private String message;
    private long timestamp = Instant.now().toEpochMilli();
    private String status;

    @JsonIgnore
    private RuntimeException exception;

    public Response() {
        this.status = ErrorCodeClient.SUCCESS.name();
    }

    public static <T> Response<T> of(T res) {
        Response<T> response = new Response<>();
        response.data = res;
        response.success();
        return response;
    }

    public static <T> Response<T> ok() {
        Response<T> response = new Response<>();
        response.success();
        return response;
    }

    public static <T> Response<T> fail(RuntimeException exception) {
        Response<T> response = new Response<>();
        response.setSuccess(false);
        response.setStatus(ErrorCodeClient.FAIL.name());
        response.setException(exception);
        return response;
    }

    public static <T> Response<T> fail(String message, RuntimeException exception) {
        Response<T> response = new Response<>();
        response.setSuccess(false);
        response.setStatus(ErrorCodeClient.FAIL.name());
        response.setException(exception);
        response.setMessage(message);
        return response;
    }

    public static <T> Response<T> fail() {
        Response<T> response = new Response<>();
        response.setSuccess(false);
        response.setStatus(ErrorCodeClient.FAIL.name());
        return response;
    }

    public Response<T> success() {
        this.success = true;
        this.code = 200;
        this.status = ErrorCodeClient.SUCCESS.name();
        return this;
    }

    public Response<T> data(T res) {
        this.data = res;
        return this;
    }

    public Response<T> success(String message) {
        this.success = true;
        this.message = message;
        this.code = 200;
        this.status = ErrorCodeClient.SUCCESS.name();
        return this;
    }

    public Response<T> fail(String message, ResponseError responseError) {
        this.success = false;
        this.code = responseError.getCode();
        this.status = ErrorCodeClient.FAIL.name();
        if (StringUtils.hasText(message)) {
            this.message = message;
        } else {
            this.message = responseError.getMessage();
        }

        return this;
    }

    public Response<T> fail(Exception ex, ResponseError responseError) {
        this.success = false;
        this.code = responseError.getCode();
        this.status = ErrorCodeClient.FAIL.name();
        this.message = ex.getMessage();
        return this;
    }

    public T getData() {
        if (this.exception != null) {
            throw this.exception;
        } else {
            return this.data;
        }
    }

    public boolean isSuccess() {
        if (this.exception != null) {
            throw this.exception;
        } else {
            return this.success;
        }
    }

    public String toString() {
        String var10000 = String.valueOf(this.data);
        return "Response {data=" + var10000 + ", success=" + this.success + ", status=" + this.status + ", code="
                + this.code + ", message='" + this.message + "', timestamp=" + this.timestamp + ", exception="
                + this.exception + "}";
    }

    @JsonIgnore
    public void setException(final RuntimeException exception) {
        this.exception = exception;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof Response)) {
            return false;
        } else {
            Response<?> other = (Response) o;
            if (!other.canEqual(this)) {
                return false;
            } else if (this.isSuccess() != other.isSuccess()) {
                return false;
            } else if (this.getCode() != other.getCode()) {
                return false;
            } else if (this.getTimestamp() != other.getTimestamp()) {
                return false;
            } else {
                Object this$data = this.getData();
                Object other$data = other.getData();
                if (this$data == null) {
                    if (other$data != null) {
                        return false;
                    }
                } else if (!this$data.equals(other$data)) {
                    return false;
                }

                Object this$message = this.getMessage();
                Object other$message = other.getMessage();
                if (this$message == null) {
                    if (other$message != null) {
                        return false;
                    }
                } else if (!this$message.equals(other$message)) {
                    return false;
                }

                Object this$status = this.getStatus();
                Object other$status = other.getStatus();
                if (this$status == null) {
                    if (other$status != null) {
                        return false;
                    }
                } else if (!this$status.equals(other$status)) {
                    return false;
                }

                Object this$exception = this.getException();
                Object other$exception = other.getException();
                if (this$exception == null) {
                    if (other$exception != null) {
                        return false;
                    }
                } else if (!this$exception.equals(other$exception)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Response;
    }

    public int hashCode() {
        int result = 1;
        result = result * 59 + (this.isSuccess() ? 79 : 97);
        result = result * 59 + this.getCode();
        long $timestamp = this.getTimestamp();
        result = result * 59 + (int) ($timestamp >>> 32 ^ $timestamp);
        Object $data = this.getData();
        result = result * 59 + ($data == null ? 43 : $data.hashCode());
        Object $message = this.getMessage();
        result = result * 59 + ($message == null ? 43 : $message.hashCode());
        Object $status = this.getStatus();
        result = result * 59 + ($status == null ? 43 : $status.hashCode());
        Object $exception = this.getException();
        result = result * 59 + ($exception == null ? 43 : $exception.hashCode());
        return result;
    }
}
