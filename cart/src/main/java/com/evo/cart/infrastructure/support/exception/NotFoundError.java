package com.evo.cart.infrastructure.support.exception;

import com.evo.common.exception.ResponseError;

import lombok.Getter;

@Getter
public enum NotFoundError implements ResponseError {
    CART_NOT_FOUND(1000001, "Cart not found"),
    ;

    private final Integer code;
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
