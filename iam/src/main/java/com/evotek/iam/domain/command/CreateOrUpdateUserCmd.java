package com.evotek.iam.domain.command;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserCmd {
    private UUID providerId;
    private String username;
    private String password;
    private String email;
    private String provider;
    private boolean twoFactorEnabled;
    private List<CreateUserRoleCmd> userRoles;
}
