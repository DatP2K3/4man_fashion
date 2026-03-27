package com.fourman.profile.infrastructure.support.exception;

import com.fourman.common.exception.ResponseError;

import lombok.Getter;

@Getter
public enum NotFoundError implements ResponseError {
    PROFILE_NOT_FOUND(1000110, "error.profile.not_found"),
    MEMBERSHIP_TIER_NOT_FOUND(1000111, "error.membership_tier.not_found"),
    CASHBACK_TRANSACTION_NOT_FOUND(1000112, "error.cashback.not_found");

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
