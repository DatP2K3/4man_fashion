package com.evo.product.infrastructure.support.exception;

import com.evo.common.exception.ResponseError;

import lombok.Getter;

@Getter
public enum BadRequestError implements ResponseError {
    PROMOTION_TYPE_IS_EXIST(1000080, "Promotion of this product is exist"),
    DISCOUNT_PRICE_OR_PERCENT_IS_REQUIRED(1000081, "Discount price or percent is required"),
    OPERATION_TYPE_IS_REQUIRED(1000082, "Operation type is required"),
    INVALID_OPERATION_TYPE(1000083, "Invalid operation type"),
    ;

    private final Integer code;
    private final String message;

    BadRequestError(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getName() {
        return name();
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public int getStatus() {
        return 400;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
