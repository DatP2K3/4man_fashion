package com.fourman.product.domain.exception;

import com.fourman.common.exception.ResponseError;

import lombok.Getter;

@Getter
public enum NotFoundError implements ResponseError {
    CATEGORY_NOT_FOUND(1000090, "error.category.not_found"),
    PRODUCT_NOT_FOUND(1000091, "error.product.not_found"),
    DISCOUNT_NOT_FOUND(1000092, "error.discount.not_found"),
    PRODUCT_VARIANT_NOT_FOUND(1000093, "error.product_variant.not_found"),
    ;

    private final Integer code;
    private final String messageKey;

    NotFoundError(int code, String messageKey) {
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
        return 404;
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
