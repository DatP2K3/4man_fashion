package com.evotek.iam.application.dto.request;

import java.util.List;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrUpdateUserRequest {
    private UUID providerId;

    @NotBlank(message = "UserName cannot be blank")
    private String username;

    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&-+=()!?\"]).{8,128}$",
            message =
                    "Password must be at least 8 characters long and contain at least one letter and one number one special character and one uppercase letter")
    private String password;

    @NotBlank(message = "Email cannot be blank")
    private String email;

    private boolean twoFactorEnabled;

    private String provider;

    private List<CreateUserRoleRequest> userRoles;
}
