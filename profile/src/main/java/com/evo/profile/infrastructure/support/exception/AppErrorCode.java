package com.evo.profile.infrastructure.support.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Getter;

@Getter
public enum AppErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    MAIL_EXISTED(1008, "Email existed, please choose another one", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1009, "Username existed, please choose another one", HttpStatus.BAD_REQUEST),
    USERNAME_IS_MISSING(1010, "Please enter username", HttpStatus.BAD_REQUEST),
    PROFILE_NOT_FOUND(1050, "Profile not found", HttpStatus.NOT_FOUND),
    MEMBERSHIP_TIER_NOT_FOUND(1051, "Membership tier not found", HttpStatus.NOT_FOUND),
    CASHBACK_TRANSACTION_NOT_FOUND(1052, "Cashback transaction not found", HttpStatus.NOT_FOUND),
    INVALID_CASHBACK_AMOUNT(1053, "Invalid cashback amount", HttpStatus.BAD_REQUEST),
    CANT_DELETE_DEFAULT_MEMBERSHIP_TIER(1054, "Can't delete default membership tier", HttpStatus.BAD_REQUEST),
    CANT_TOGGLE_VISIBILITY_DEFAULT_MEMBERSHIP_TIER(1055, "Can't toggle visibility of default membership tier", HttpStatus.BAD_REQUEST)
    ;

    private final int code;
    private final HttpStatusCode statusCode;
    private final String message;

    AppErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }
}
