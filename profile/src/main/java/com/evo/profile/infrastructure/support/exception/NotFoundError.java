package com.evo.profile.infrastructure.support.exception;

import com.evo.common.exception.ResponseError;

import lombok.Getter;

@Getter
public enum NotFoundError implements ResponseError {
    PROFILE_NOT_FOUND(1000110, "Profile not found"),
    MEMBERSHIP_TIER_NOT_FOUND(1000111, "Membership tier not found"),
    CASHBACK_TRANSACTION_NOT_FOUND(1000112, "Cashback transaction not found");

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
