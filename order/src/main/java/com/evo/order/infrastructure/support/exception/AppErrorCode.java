package com.evo.order.infrastructure.support.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Getter;

@Getter
public enum AppErrorCode {
    ORDER_NOT_FOUND(1189, "Order not found", HttpStatus.NOT_FOUND),
    ORDER_ITEM_NOT_FOUND(1190, "Order item not found", HttpStatus.NOT_FOUND),
    CANT_DELETE_ORDER(1191, "Can't delete order", HttpStatus.BAD_REQUEST),
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
