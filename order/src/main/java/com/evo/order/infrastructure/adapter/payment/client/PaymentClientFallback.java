package com.evo.order.infrastructure.adapter.payment.client;

import com.evo.common.dto.request.GetPaymentUrlRequest;
import com.evo.common.dto.response.ApiResponses;
import com.evo.common.dto.response.CartDTO;
import com.evo.common.enums.ServiceUnavailableError;
import com.evo.common.exception.ForwardInnerAlertException;
import com.evo.common.exception.ResponseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class PaymentClientFallback
        implements FallbackFactory<
        PaymentClient> {
    @Override
    public PaymentClient create(Throwable cause) {
        return new FallbackWithFactory(cause);
    }

    @Slf4j
    static class FallbackWithFactory implements PaymentClient {
        private final Throwable cause;

        FallbackWithFactory(Throwable cause) {
            this.cause = cause;
        }

         @Override
        public ApiResponses<String> getPaymentUrl(GetPaymentUrlRequest getPaymentUrlRequest) {
            if (cause instanceof ForwardInnerAlertException) {
                throw (RuntimeException) cause;
            }
            throw new ResponseException(ServiceUnavailableError.PAYMENT_SERVICE_UNAVAILABLE_ERROR);
        }
    }
}
