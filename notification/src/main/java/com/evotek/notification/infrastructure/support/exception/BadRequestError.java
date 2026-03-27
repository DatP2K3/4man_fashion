package com.evotek.notification.infrastructure.support.exception;

import com.evo.common.exception.ResponseError;

import lombok.Getter;

@Getter
public enum BadRequestError implements ResponseError {
    CANT_SEND_EMAIL(1000030, "error.notification.cant_send_email"),
    FIREBASE_SUBSCRIBE_TOPIC_FAILED(1000031, "error.firebase.subscribe_failed"),
    FIREBASE_SEND_NOTIFICATION_FAILED(1000032, "error.firebase.send_failed"),
    DEVICE_REGISTRATION_ALREADY_EXISTS(1000033, "error.notification.device_exists"),
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
