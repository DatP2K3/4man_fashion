package com.evotek.notification.infrastructure.support.exception;

import com.evo.common.exception.ResponseError;

import lombok.Getter;

@Getter
public enum BadRequestError implements ResponseError {
    CANT_SEND_EMAIL(1000030, "Can't send Email"),
    FIREBASE_SUBSCRIBE_TOPIC_FAILED(1000031, "Failed to subscribe to topic"),
    FIREBASE_SEND_NOTIFICATION_FAILED(1000032, "Failed to send notification"),
    DEVICE_REGISTRATION_ALREADY_EXISTS(1000033, "Device registration already exists"),
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
