package com.evo.payment.infrastructure.support.exception;

import com.evo.common.exception.ResponseError;

import lombok.Getter;

@Getter
public enum NotFoundError implements ResponseError {
    PAYMENT_TRANSACTION_NOT_FOUND(1000070, "Payment transaction not found"),
    ;

    private final int code;
    private final String message;

    NotFoundError(int code, String message) {
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
        return 404;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
