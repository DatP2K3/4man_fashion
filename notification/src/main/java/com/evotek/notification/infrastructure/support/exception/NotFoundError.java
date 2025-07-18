package com.evotek.notification.infrastructure.support.exception;

import com.evo.common.exception.ResponseError;

import lombok.Getter;

@Getter
public enum NotFoundError implements ResponseError {
    DEVICE_REGISTRATION_NOT_FOUND(1000040, "Device registration not found"),
    NOTIFICATION_NOT_FOUND(1000041, "Notification not found"),
    USER_TOPIC_NOT_FOUND(1000042, "User topic not found"),
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
