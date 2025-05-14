package com.evo.common.enums;

import com.evo.common.dto.error.ResponseError;

public enum ServiceUnavailableError implements ResponseError {
    SERVICE_UNAVAILABLE_ERROR(50300001, "Service unavailable"),
    IAM_SERVICE_UNAVAILABLE_ERROR(50300002, "IAM Service unavailable"),
    STORAGE_SERVICE_UNAVAILABLE_ERROR(50300003, "Storage Service unavailable"),
    NOTIFICATION_SERVICE_UNAVAILABLE_ERROR(50300004, "Notification Service unavailable"),
    PAYMENT_SERVICE_UNAVAILABLE_ERROR(50300005, "Payment Service unavailable"),
    PRODUCT_SERVICE_UNAVAILABLE_ERROR(50300006, "Product Service unavailable"),
    ORDER_SERVICE_UNAVAILABLE_ERROR(50300007, "Order Service unavailable"),
    CART_SERVICE_UNAVAILABLE_ERROR(50300008, "Cart Service unavailable"),
    LOCATION_SERVICE_UNAVAILABLE_ERROR(50300009, "Location Service unavailable"),
    GHN_SERVICE_UNAVAILABLE_ERROR(50300010, "GHN Service unavailable"),
    ;

    private final Integer code;
    private final String message;

    ServiceUnavailableError(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getName() {
        return this.name();
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public int getStatus() {
        return 503;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }
}
