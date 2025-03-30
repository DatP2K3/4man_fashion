package com.evo.profile.domain.command;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOrUpdateMembershipTierCmd {
    UUID id;
    private String name;
    private Double cashbackPercentage;
    private Integer minPoints;
    private Boolean defaultTier;
}
