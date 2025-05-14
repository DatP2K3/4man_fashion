package com.evo.profile.domain.command;

import java.util.UUID;

import com.evo.common.enums.MembershipTierType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOrUpdateMembershipTierCmd {
    UUID id;
    private MembershipTierType name;
    private Double cashbackPercentage;
    private Integer minPoints;
    private Boolean defaultTier;
}
