package com.fourman.order.infrastructure.adapter.profile.client;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import com.fourman.common.dto.response.ProfileDTO;
import com.fourman.common.dto.response.Response;
import com.fourman.common.enums.ServiceUnavailableError;
import com.fourman.common.exception.ForwardInnerAlertException;
import com.fourman.common.exception.ResponseException;

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
        public Response<ProfileDTO> getProfile() {
            if (cause instanceof ForwardInnerAlertException) {
                throw (RuntimeException) cause;
            }
            throw new ResponseException(ServiceUnavailableError.STORAGE_SERVICE_UNAVAILABLE_ERROR);
        }
    }
}
