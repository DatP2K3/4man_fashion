package com.evo.profile.infrastructure.support.exception;

import com.evo.common.exception.ResponseError;

import lombok.Getter;

@Getter
public enum BadRequestError implements ResponseError {
    UNCATEGORIZED_EXCEPTION(1000100, "Uncategorized error"),
    MAIL_EXISTED(1000101, "Email existed, please choose another one"),
    USER_EXISTED(1000102, "Username existed, please choose another one"),
    USERNAME_IS_MISSING(1000103, "Please enter username"),
    INVALID_CASHBACK_AMOUNT(1000104, "Invalid cashback amount"),
    CANT_DELETE_DEFAULT_MEMBERSHIP_TIER(1000105, "Can't delete default membership tier"),
    CANT_TOGGLE_VISIBILITY_DEFAULT_MEMBERSHIP_TIER(1000106, "Can't toggle visibility of default membership tier");

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
