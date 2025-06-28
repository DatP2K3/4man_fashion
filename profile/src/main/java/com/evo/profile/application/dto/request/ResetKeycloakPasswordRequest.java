package com.evo.profile.application.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(
        value = {"type", "temporary"},
        allowSetters = true)
public class ResetKeycloakPasswordRequest {
    private String type = "password";
    private String value;
    private boolean temporary = false;
}
