package com.evo.order.infrastructure.support.exception;

import com.evo.common.exception.ResponseError;

import lombok.Getter;

@Getter
public enum BadRequestError implements ResponseError {
    ORDER_NOT_FOUND(1000050, "Order not found"),
    ORDER_ITEM_NOT_FOUND(1000051, "Order item not found"),
    CANT_DELETE_ORDER(1000052, "Can't delete order"),
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
