package com.evo.order.infrastructure.support.exception;

import com.evo.common.exception.ResponseError;

import lombok.Getter;

@Getter
public enum NotFoundError implements ResponseError {
    ORDER_NOT_FOUND(1000060, "error.order.not_found"),
    ORDER_ITEM_NOT_FOUND(1000061, "error.order.not_found"),
    CANT_DELETE_ORDER(1000062, "error.order.cannot_cancel"),
    ;

    private final Integer code;
    private final String messageKey;

    NotFoundError(int code, String messageKey) {
        this.code = code;
        this.messageKey = messageKey;
    }

    @Override
    public String getName() { return name(); }

    @Override
    public String getMessage() { return messageKey; }

    @Override
    public int getStatus() { return 404; }

    @Override
    public Integer getCode() { return code; }

    @Override
    public String getMessageKey() { return messageKey; }
}
