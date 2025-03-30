package com.evo.profile.application.dto.request;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrUpdateMembershipTierRequest {
    private UUID id;
    private String name;
    private Double cashbackPercentage;
    private Integer minPoints;
    private boolean defaultTier;
}
