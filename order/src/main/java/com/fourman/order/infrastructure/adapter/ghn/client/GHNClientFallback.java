package com.fourman.order.infrastructure.adapter.ghn.client;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import com.fourman.common.dto.response.Response;
import com.fourman.common.enums.ServiceUnavailableError;
import com.fourman.common.exception.ForwardInnerAlertException;
import com.fourman.common.exception.ResponseException;
import com.fourman.order.application.dto.request.CreateGHNOrderRequest;
import com.fourman.order.application.dto.request.GetGHNFeeRequest;
import com.fourman.order.application.dto.request.GetGHNOrderDetailRequest;
import com.fourman.order.application.dto.request.PrintOrCancelGHNOrderRequest;
import com.fourman.order.application.dto.response.GHNFeeDTO;
import com.fourman.order.application.dto.response.GHNOrderDTO;
import com.fourman.order.application.dto.response.GHNOrderDetailDTO;
import com.fourman.order.application.dto.response.GHNPrintTokenDTO;

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
        public Response<GHNFeeDTO> calculateShippingFee(GetGHNFeeRequest request) {
            if (cause instanceof ForwardInnerAlertException) {
                return Response.fail((RuntimeException) cause);
            }
            return Response.fail(new ResponseException(ServiceUnavailableError.GHN_SERVICE_UNAVAILABLE_ERROR));
        }

        @Override
        public Response<GHNOrderDTO> createShippingOrder(CreateGHNOrderRequest request) {
            if (cause instanceof ForwardInnerAlertException) {
                return Response.fail((RuntimeException) cause);
            }
            return Response.fail(new ResponseException(ServiceUnavailableError.GHN_SERVICE_UNAVAILABLE_ERROR));
        }

        @Override
        public Response<GHNPrintTokenDTO> getPrintToken(PrintOrCancelGHNOrderRequest request) {
            if (cause instanceof ForwardInnerAlertException) {
                return Response.fail((RuntimeException) cause);
            }
            return Response.fail(new ResponseException(ServiceUnavailableError.GHN_SERVICE_UNAVAILABLE_ERROR));
        }

        @Override
        public Response<String> print(String token) {
            if (cause instanceof ForwardInnerAlertException) {
                return Response.fail((RuntimeException) cause);
            }
            return Response.fail(new ResponseException(ServiceUnavailableError.GHN_SERVICE_UNAVAILABLE_ERROR));
        }

        @Override
        public Response<Void> cancelShippingOrder(PrintOrCancelGHNOrderRequest request) {
            if (cause instanceof ForwardInnerAlertException) {
                return Response.fail((RuntimeException) cause);
            }
            return Response.fail(new ResponseException(ServiceUnavailableError.GHN_SERVICE_UNAVAILABLE_ERROR));
        }

        @Override
        public Response<GHNOrderDetailDTO> getOrderDetail(GetGHNOrderDetailRequest request) {
            if (cause instanceof ForwardInnerAlertException) {
                return Response.fail((RuntimeException) cause);
            }
            return Response.fail(new ResponseException(ServiceUnavailableError.GHN_SERVICE_UNAVAILABLE_ERROR));
        }
    }
}
