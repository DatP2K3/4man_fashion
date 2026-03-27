package com.fourman.common.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fourman.common.dto.response.Response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Represent http response body
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@SuperBuilder
public class ErrorResponse<T> extends Response<T> {
    private String error;
}
