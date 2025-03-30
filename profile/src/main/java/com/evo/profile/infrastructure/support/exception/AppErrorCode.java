package com.evo.profile.infrastructure.support.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Getter;

@Getter
public enum AppErrorCode {
    PROFILE_NOT_FOUND(1050, "Profile not found", HttpStatus.NOT_FOUND),
    MEMBERSHIP_TIER_NOT_FOUND(1051, "Membership tier not found", HttpStatus.NOT_FOUND);

    private final int code;
    private final HttpStatusCode statusCode;
    private final String message;

    AppErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }
}
