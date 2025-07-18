package com.evo.order.infrastructure.support.exception;

import com.evo.common.exception.ResponseError;

import lombok.Getter;

@Getter
public enum NotFoundError implements ResponseError {
    ORDER_NOT_FOUND(1000060, "Order not found"),
    ORDER_ITEM_NOT_FOUND(1000061, "Order item not found"),
    CANT_DELETE_ORDER(1000062, "Can't delete order"),
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
