package com.evotek.iam.domain.command;

import java.util.List;
import java.util.UUID;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrUpdateUserCmd {
    private UUID providerId;
    private String username;
    private String password;
    private String email;
    private String provider;
    private Boolean twoFactorEnabled;
    private List<CreateUserRoleCmd> userRoles;
}
