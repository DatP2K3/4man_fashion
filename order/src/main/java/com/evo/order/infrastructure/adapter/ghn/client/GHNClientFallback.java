package com.evo.order.infrastructure.adapter.ghn.client;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import com.evo.common.dto.response.ApiResponses;
import com.evo.common.enums.ServiceUnavailableError;
import com.evo.common.exception.ForwardInnerAlertException;
import com.evo.common.exception.ResponseException;
import com.evo.order.application.dto.request.CreateGHNOrderRequest;
import com.evo.order.application.dto.request.GetGHNFeeRequest;
import com.evo.order.application.dto.request.GetGHNOrderDetailRequest;
import com.evo.order.application.dto.request.PrintOrCancelGHNOrderRequest;
import com.evo.order.application.dto.response.GHNFeeDTO;
import com.evo.order.application.dto.response.GHNOrderDTO;
import com.evo.order.application.dto.response.GHNOrderDetailDTO;
import com.evo.order.application.dto.response.GHNPrintTokenDTO;

import lombok.extern.slf4j.Slf4j;

@Component
public class GHNClientFallback implements FallbackFactory<GHNClient> {
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
            throw new ResponseException(ServiceUnavailableError.GHN_SERVICE_UNAVAILABLE_ERROR);
        }

        @Override
        public ApiResponses<GHNOrderDTO> createShippingOrder(CreateGHNOrderRequest request) {
            if (cause instanceof ForwardInnerAlertException) {
                throw (RuntimeException) cause;
            }
            throw new ResponseException(ServiceUnavailableError.GHN_SERVICE_UNAVAILABLE_ERROR);
        }

        @Override
        public ApiResponses<GHNPrintTokenDTO> getPrintToken(PrintOrCancelGHNOrderRequest request) {
            if (cause instanceof ForwardInnerAlertException) {
                throw (RuntimeException) cause;
            }
            throw new ResponseException(ServiceUnavailableError.GHN_SERVICE_UNAVAILABLE_ERROR);
        }

        @Override
        public String print(String token) {
            if (cause instanceof ForwardInnerAlertException) {
                throw (RuntimeException) cause;
            }
            throw new ResponseException(ServiceUnavailableError.GHN_SERVICE_UNAVAILABLE_ERROR);
        }

        @Override
        public ApiResponses<Void> cancelShippingOrder(PrintOrCancelGHNOrderRequest request) {
            if (cause instanceof ForwardInnerAlertException) {
                throw (RuntimeException) cause;
            }
            throw new ResponseException(ServiceUnavailableError.GHN_SERVICE_UNAVAILABLE_ERROR);
        }

        @Override
        public ApiResponses<GHNOrderDetailDTO> getOrderDetail(GetGHNOrderDetailRequest request) {
            if (cause instanceof ForwardInnerAlertException) {
                throw (RuntimeException) cause;
            }
            throw new ResponseException(ServiceUnavailableError.GHN_SERVICE_UNAVAILABLE_ERROR);
        }
    }
}
