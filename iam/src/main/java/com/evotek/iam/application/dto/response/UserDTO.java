package com.evotek.iam.application.dto.response;

import java.util.UUID;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private UUID id;
    private String provider;
    private UUID providerId;
    private String email;
    private String username;
}
