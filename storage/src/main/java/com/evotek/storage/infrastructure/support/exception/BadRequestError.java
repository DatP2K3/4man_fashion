package com.evotek.storage.infrastructure.support.exception;

import com.evo.common.exception.ResponseError;

import lombok.Getter;

@Getter
public enum BadRequestError implements ResponseError {
    CANT_CREATE_DIR(1000140, "Can't create directory"),
    INVALID_FILENAME(1000141, "Invalid filename"),
    CANT_STORE_FILE(1000142, "Can't store file"),
    CANT_HASH_FILE_NAME(1000143, "Can't hash file name"),
    FILE_EXTENSION_NOT_ALLOWED(1000144, "File extension not allowed"),
    FILE_TYPE_NOT_ALLOWED(1000145, "File type not allowed"),
    CANT_DELETE_FILE(1000146, "Can't delete file"),
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
