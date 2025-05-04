package com.evo.order.infrastructure.adapter.ghn.client;

import com.evo.common.dto.response.ApiResponses;
import com.evo.common.dto.response.CartDTO;
import com.evo.common.enums.ServiceUnavailableError;
import com.evo.common.exception.ForwardInnerAlertException;
import com.evo.common.exception.ResponseException;
import com.evo.order.application.dto.request.GetGHNFeeRequest;
import com.evo.order.application.dto.response.GHNFeeDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class GHNClientFallback
        implements FallbackFactory<
        GHNClient> {
    @Override
    public GHNClient create(Throwable cause) {
        return new FallbackWithFactory(cause);
    }

    @Slf4j
    static class FallbackWithFactory implements GHNClient {
        private final Throwable cause;

        FallbackWithFactory(Throwable cause) {
            this.cause = cause;
        }

        @Override
        public ApiResponses<GHNFeeDTO> calculateShippingFee(GetGHNFeeRequest request) {
            if (cause instanceof ForwardInnerAlertException) {
                throw (RuntimeException) cause;
            }
            throw new ResponseException(ServiceUnavailableError.STORAGE_SERVICE_UNAVAILABLE_ERROR);
        }
    }
}
