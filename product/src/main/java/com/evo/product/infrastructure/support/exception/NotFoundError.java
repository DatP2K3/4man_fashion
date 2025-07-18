package com.evo.product.infrastructure.support.exception;

import com.evo.common.exception.ResponseError;

import lombok.Getter;

@Getter
public enum NotFoundError implements ResponseError {
    CATEGORY_NOT_FOUND(1000090, "Category not found"),
    PRODUCT_NOT_FOUND(1000091, "Product not found"),
    DISCOUNT_NOT_FOUND(1000092, "Discount not found"),
    PRODUCT_VARIANT_NOT_FOUND(1000093, "Product variant not found"),
    ;

    private final Integer code;
    private final String message;

    NotFoundError(int code, String message) {
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
        return 404;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
