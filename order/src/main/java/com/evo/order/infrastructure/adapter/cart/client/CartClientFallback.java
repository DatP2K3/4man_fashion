package com.evo.order.infrastructure.adapter.cart.client;

import com.evo.common.dto.response.ApiResponses;
import com.evo.common.dto.response.CartDTO;
import com.evo.common.enums.ServiceUnavailableError;
import com.evo.common.exception.ForwardInnerAlertException;
import com.evo.common.exception.ResponseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CartClientFallback
        implements FallbackFactory<
        CartClient> {
    @Override
    public CartClient create(Throwable cause) {
        return new FallbackWithFactory(cause);
    }

    @Slf4j
    static class FallbackWithFactory implements CartClient {
        private final Throwable cause;

        FallbackWithFactory(Throwable cause) {
            this.cause = cause;
        }

        @Override
        public ApiResponses<CartDTO> getCart() {
            if (cause instanceof ForwardInnerAlertException) {
                throw (RuntimeException) cause;
            }
            throw new ResponseException(ServiceUnavailableError.STORAGE_SERVICE_UNAVAILABLE_ERROR);
        }
    }
}
