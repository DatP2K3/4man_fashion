package com.fourman.cart.infrastructure.adapter.product.client;

import java.util.UUID;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import com.fourman.common.dto.response.ProductDTO;
import com.fourman.common.dto.response.Response;
import com.fourman.common.enums.ServiceUnavailableError;
import com.fourman.common.exception.ForwardInnerAlertException;
import com.fourman.common.exception.ResponseException;

import lombok.extern.slf4j.Slf4j;

@Component
public class ProductClientFallback implements FallbackFactory<ProductClient> {
    @Override
    public ProductClient create(Throwable cause) {
        return new FallbackWithFactory(cause);
    }

    @Slf4j
    static class FallbackWithFactory implements ProductClient {
        private final Throwable cause;

        FallbackWithFactory(Throwable cause) {
            this.cause = cause;
        }

        @Override
        public Response<ProductDTO> getProduct(UUID fileId) {
            if (cause instanceof ForwardInnerAlertException) {
                return Response.fail((RuntimeException) cause);
            }
            return Response.fail(new ResponseException(ServiceUnavailableError.PRODUCT_SERVICE_UNAVAILABLE_ERROR));
        }
    }
}
