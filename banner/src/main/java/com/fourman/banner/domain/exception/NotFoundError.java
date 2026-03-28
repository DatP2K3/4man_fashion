package com.fourman.banner.domain.exception;

import com.fourman.common.exception.ResponseError;

import lombok.Getter;

@Getter
public enum NotFoundError implements ResponseError {
    BANNER_NOT_FOUND(2023, "error.banner.not_found"),
    ;

    private final Integer code;
    private final String messageKey;

    NotFoundError(int code, String messageKey) {
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
        return 404;
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
