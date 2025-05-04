package com.evo.order.infrastructure.adapter.profile.client;

import com.evo.common.dto.response.ApiResponses;
import com.evo.common.dto.response.CartDTO;
import com.evo.common.dto.response.ProfileDTO;
import com.evo.common.enums.ServiceUnavailableError;
import com.evo.common.exception.ForwardInnerAlertException;
import com.evo.common.exception.ResponseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class ProfileClientFallback
        implements FallbackFactory<
        ProfileClient> {
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
        public ApiResponses<ProfileDTO> getProfile() {
            if (cause instanceof ForwardInnerAlertException) {
                throw (RuntimeException) cause;
            }
            throw new ResponseException(ServiceUnavailableError.STORAGE_SERVICE_UNAVAILABLE_ERROR);
        }
    }
}
