package com.fourman.common.exception;

public interface ResponseError {
    String getName();

    String getMessage();

    int getStatus();

    default Integer getCode() {
        return 0;
    }

    /**
     * Returns the i18n message key for this error.
     * Override in each enum to provide the correct key.
     */
    default String getMessageKey() {
        return getMessage();
    }
}
