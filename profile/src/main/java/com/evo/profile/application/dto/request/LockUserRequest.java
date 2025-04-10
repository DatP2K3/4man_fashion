package com.evo.profile.application.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LockUserRequest {
    private boolean enabled;
}
