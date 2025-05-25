package com.evo.dashboard.adapter.order.client;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import com.evo.common.dto.response.ApiResponses;
import com.evo.common.dto.response.OrderDTO;
import com.evo.common.enums.OrderStatus;
import com.evo.common.enums.ServiceUnavailableError;
import com.evo.common.exception.ForwardInnerAlertException;
import com.evo.common.exception.ResponseException;

import lombok.extern.slf4j.Slf4j;

@Component
public class OrderClientFallback implements FallbackFactory<OrderClient> {
    @Override
    public OrderClient create(Throwable cause) {
        return new FallbackWithFactory(cause);
    }

    @Slf4j
    static class FallbackWithFactory implements OrderClient {
        private final Throwable cause;

        FallbackWithFactory(Throwable cause) {
            this.cause = cause;
        }

        @Override
        public ApiResponses<List<OrderDTO>> searchOrders(
                String keyword,
                UUID userId,
                OrderStatus orderStatus,
                Instant startDate,
                Instant endDate,
                Boolean printed,
                int pageIndex,
                int pageSize,
                String sortBy) {
            if (cause instanceof ForwardInnerAlertException) {
                throw (RuntimeException) cause;
            }
            throw new ResponseException(ServiceUnavailableError.ORDER_SERVICE_UNAVAILABLE_ERROR);
        }
    }
}
