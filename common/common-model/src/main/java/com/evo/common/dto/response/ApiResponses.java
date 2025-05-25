package com.evo.common.dto.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponses<T> implements Serializable {
    private T data;
    private boolean success;
    private int code;
    private String message;
    private long timestamp;
    private String status;

    @JsonIgnore
    private RuntimeException exception;

    public static <T> ApiResponses<T> fail(RuntimeException exception) {
        ApiResponses<T> response = new ApiResponses<>();
        response.setMessage(exception.getMessage());
        response.setSuccess(false);
        response.setCode(500);
        response.setException(exception);
        response.setTimestamp(System.currentTimeMillis());
        return response;
    }

    public static <T> ApiResponses<T> success(T data) {
        return success(data, "xin chào");
    }

    public static <T> ApiResponses<T> success(T data, String message) {
        ApiResponses<T> response = new ApiResponses<>();
        response.setData(data);
        response.setMessage(message);
        response.setSuccess(true);
        response.setCode(200);
        response.setTimestamp(System.currentTimeMillis());
        response.setStatus("OK");
        return response;
    }
}
