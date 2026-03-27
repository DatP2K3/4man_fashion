package com.evo.profile.application.dto.request;

import java.util.UUID;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import com.evo.common.enums.MembershipTierType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrUpdateMembershipTierRequest {
    private UUID id;

    @NotNull
    private MembershipTierType name;

    @NotNull
    @Min(0)
    private Double cashbackPercentage;

    @NotNull
    @Min(0)
    private Integer minPoints;

    private boolean defaultTier;
}
