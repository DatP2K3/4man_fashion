package com.fourman.order.domain.exception;

import com.fourman.common.exception.ResponseError;

import lombok.Getter;

@Getter
public enum BadRequestError implements ResponseError {
    ORDER_NOT_FOUND(1000050, "error.order.not_found"),
    ORDER_ITEM_NOT_FOUND(1000051, "error.order.not_found"),
    CANT_DELETE_ORDER(1000052, "error.order.cannot_cancel"),
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
