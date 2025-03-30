package com.evo.product.infrastructure.support.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum AppErrorCode {
    CATEGORY_NOT_FOUND(1045, "Category not found", HttpStatus.NOT_FOUND),
    PRODUCT_NOT_FOUND(1045, "Product not found", HttpStatus.NOT_FOUND),
    ;

    private final int code;
    private final HttpStatusCode statusCode;
    private final String message;

    AppErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }
}
