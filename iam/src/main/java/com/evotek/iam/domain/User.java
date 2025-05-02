package com.evotek.iam.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.evo.common.Auditor;
import com.evotek.iam.domain.command.CreateOrUpdateUserCmd;
import com.evotek.iam.domain.command.CreateUserRoleCmd;
import com.evotek.iam.infrastructure.support.IdUtils;

import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Setter
@Getter
public class User extends Auditor {
    private UUID id;
    private UUID providerId;
    private String username;
    private String email;
    private String password;
    private boolean locked;
    private String provider;
    private Boolean twoFactorEnabled;
    private List<UserRole> userRoles;
    private UserActivityLog userActivityLog;

    public User(CreateOrUpdateUserCmd cmd) {
        this.id = IdUtils.nextId();
        this.username = cmd.getUsername();
        this.password = cmd.getPassword();
        this.email = cmd.getEmail();
        this.provider = cmd.getProvider();
        this.locked = false;
        this.twoFactorEnabled = false;
        this.providerId = cmd.getProviderId();
        if (cmd.getUserRoles() != null) {
            createOrUpdateUserRole(cmd.getUserRoles());
        }
    }

    public void update(CreateOrUpdateUserCmd cmd) {
        if (cmd.getEmail() != null) {
            this.email = cmd.getEmail();
        }

        if (cmd.getTwoFactorEnabled() != null) {
            this.twoFactorEnabled = cmd.getTwoFactorEnabled();
        }

        if (cmd.getUserRoles() != null) {
            createOrUpdateUserRole(cmd.getUserRoles());
        }
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    private void createOrUpdateUserRole(List<CreateUserRoleCmd> userRoles) {
        if (this.userRoles == null) {
            this.userRoles = new ArrayList<>();
        }
        userRoles.forEach(userRole -> {
            this.userRoles.add(new UserRole(userRole.getRoleId(), this.id));
        });
    }
}
