package com.evo.profile.application.dto.response;

import java.util.UUID;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MembershipTierDTO {
    private UUID id;
    private String name;
    private Double cashbackPercentage;
    private Integer minPoints;
    private boolean deleted;
    private boolean defaultTier;
}
