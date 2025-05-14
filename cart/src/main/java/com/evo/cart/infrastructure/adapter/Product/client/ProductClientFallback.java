package com.evo.cart.infrastructure.adapter.Product.client;

import java.util.UUID;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import com.evo.common.dto.response.ApiResponses;
import com.evo.common.dto.response.ProductDTO;
import com.evo.common.enums.ServiceUnavailableError;
import com.evo.common.exception.ForwardInnerAlertException;
import com.evo.common.exception.ResponseException;

import lombok.extern.slf4j.Slf4j;

@Component
public class ProductClientFallback
        implements FallbackFactory<
                ProductClient> { // FallbackFactory: Dùng để xử lý khi gặp lỗi khi gọi api từ Iam Client
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
        public ApiResponses<ProductDTO> getProduct(UUID fileId) {
            if (cause instanceof ForwardInnerAlertException) {
                throw (RuntimeException) cause;
            }
            throw new ResponseException(ServiceUnavailableError.STORAGE_SERVICE_UNAVAILABLE_ERROR);
        }
    }
}
