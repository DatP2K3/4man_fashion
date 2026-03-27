package com.fourman.product.infrastructure.support.exception;

import com.fourman.common.exception.ResponseError;

import lombok.Getter;

@Getter
public enum BadRequestError implements ResponseError {
    PROMOTION_TYPE_IS_EXIST(1000080, "error.promotion_type_exists"),
    DISCOUNT_PRICE_OR_PERCENT_IS_REQUIRED(1000081, "error.discount_price_or_percent_required"),
    OPERATION_TYPE_IS_REQUIRED(1000082, "error.operation_type_required"),
    INVALID_OPERATION_TYPE(1000083, "error.invalid_operation_type"),
    ;

    private final Integer code;
    private final String messageKey;

    BadRequestError(int code, String messageKey) {
        this.code = code;
        this.messageKey = messageKey;
    }

    @Override
    public String getName() {
        return name();
    }

    @Override
    public String getMessage() {
        return messageKey;
    }

    @Override
    public int getStatus() {
        return 400;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessageKey() {
        return messageKey;
    }
}
