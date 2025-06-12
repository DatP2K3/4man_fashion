package com.evo.dashboard.adapter.user.client;

import java.time.Instant;
import java.util.List;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import com.evo.common.dto.response.PageApiResponse;
import com.evo.common.dto.response.ProfileDTO;
import com.evo.common.enums.ServiceUnavailableError;
import com.evo.common.exception.ForwardInnerAlertException;
import com.evo.common.exception.ResponseException;

import lombok.extern.slf4j.Slf4j;

@Component
public class ProfileClientFallback implements FallbackFactory<ProfileClient> {
    @Override
    public ProfileClient create(Throwable cause) {
        return new FallbackWithFactory(cause);
    }

    @Slf4j
    static class FallbackWithFactory implements ProfileClient {
        private final Throwable cause;

        FallbackWithFactory(Throwable cause) {
            this.cause = cause;
        }

        @Override
        public PageApiResponse<List<ProfileDTO>> searchProfiles(
                String keyword, Instant createdFrom, Instant createdTo) {
            if (cause instanceof ForwardInnerAlertException) {
                throw (RuntimeException) cause;
            }
            throw new ResponseException(ServiceUnavailableError.ORDER_SERVICE_UNAVAILABLE_ERROR);
        }
    }
}
