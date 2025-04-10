package com.evo.profile.application.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonIgnoreProperties(
        value = {"type", "temporary"},
        allowSetters = true)
public class ResetKeycloakPasswordRequest {
    private String type = "password";
    private String value;
    private boolean temporary = false;
}
