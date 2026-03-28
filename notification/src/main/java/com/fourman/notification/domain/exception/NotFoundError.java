package com.fourman.notification.domain.exception;

import com.fourman.common.exception.ResponseError;

import lombok.Getter;

@Getter
public enum NotFoundError implements ResponseError {
    DEVICE_REGISTRATION_NOT_FOUND(1000040, "error.device_registration.not_found"),
    NOTIFICATION_NOT_FOUND(1000041, "error.notification.not_found"),
    USER_TOPIC_NOT_FOUND(1000042, "error.user_topic.not_found"),
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
