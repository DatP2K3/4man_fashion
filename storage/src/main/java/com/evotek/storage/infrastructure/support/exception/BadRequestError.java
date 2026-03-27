package com.evotek.storage.infrastructure.support.exception;

import com.evo.common.exception.ResponseError;

import lombok.Getter;

@Getter
public enum BadRequestError implements ResponseError {
    CANT_CREATE_DIR(1000140, "error.file.upload_failed"),
    INVALID_FILENAME(1000141, "error.file.invalid_type"),
    CANT_STORE_FILE(1000142, "error.file.upload_failed"),
    CANT_HASH_FILE_NAME(1000143, "error.file.upload_failed"),
    FILE_EXTENSION_NOT_ALLOWED(1000144, "error.file.invalid_type"),
    FILE_TYPE_NOT_ALLOWED(1000145, "error.file.invalid_type"),
    CANT_DELETE_FILE(1000146, "error.file.upload_failed"),
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
