package com.evo.product.infrastructure.support.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Getter;

@Getter
public enum AppErrorCode {
    CATEGORY_NOT_FOUND(1045, "Category not found", HttpStatus.NOT_FOUND),
    PRODUCT_NOT_FOUND(1046, "Product not found", HttpStatus.NOT_FOUND),
    PROMOTION_TYPE_IS_EXIST(1047, "Promotion of this product is exist", HttpStatus.BAD_REQUEST),
    DISCOUNT_PRICE_OR_PERCENT_IS_REQUIRED(1048, "Discount price or percent is required", HttpStatus.BAD_REQUEST),
    DISCOUNT_NOT_FOUND(1049, "Discount not found", HttpStatus.NOT_FOUND),
    PRODUCT_VARIANT_NOT_FOUND(1050, "Product variant not found", HttpStatus.NOT_FOUND),
    OPERATION_TYPE_IS_REQUIRED(1051, "Operation type is required", HttpStatus.BAD_REQUEST),
    INVALID_OPERATION_TYPE(1052, "Invalid operation type", HttpStatus.BAD_REQUEST),
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
