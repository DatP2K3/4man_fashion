package com.fourman.profile.domain.exception;

import com.fourman.common.exception.ResponseError;

import lombok.Getter;

@Getter
public enum BadRequestError implements ResponseError {
    UNCATEGORIZED_EXCEPTION(1000100, "error.internal_server"),
    MAIL_EXISTED(1000101, "error.profile.mail_existed"),
    USER_EXISTED(1000102, "error.profile.user_existed"),
    USERNAME_IS_MISSING(1000103, "error.profile.username_missing"),
    INVALID_CASHBACK_AMOUNT(1000104, "error.profile.invalid_cashback"),
    CANT_DELETE_DEFAULT_MEMBERSHIP_TIER(1000105, "error.profile.cant_delete_default_tier"),
    CANT_TOGGLE_VISIBILITY_DEFAULT_MEMBERSHIP_TIER(1000106, "error.profile.cant_toggle_default_tier");

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
