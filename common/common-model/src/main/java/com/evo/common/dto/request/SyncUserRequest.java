package com.evo.common.dto.request;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SyncUserRequest {
    private UUID selfUserID;
    private UUID providerId;
    private String username;
    private String email;
    private String password;
    private boolean locked;
    private String provider;
    private List<UUID> roleIds;
}
