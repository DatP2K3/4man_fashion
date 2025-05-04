package com.evo.profile.domain;

import java.util.UUID;

import com.evo.common.Auditor;
import com.evo.profile.domain.command.CreateOrUpdateMembershipTierCmd;
import com.evo.profile.infrastructure.support.IdUtils;
import com.evo.common.enums.MembershipTierType;

import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Setter
@Getter
public class MembershipTier extends Auditor {
    private UUID id;
    private MembershipTierType name;
    private Double cashbackPercentage;
    private Integer minPoints;
    private boolean deleted;
    private boolean defaultTier;

    public MembershipTier(CreateOrUpdateMembershipTierCmd createMembershipTierCmd) {
        this.id = IdUtils.nextId();
        this.name = createMembershipTierCmd.getName();
        this.cashbackPercentage = createMembershipTierCmd.getCashbackPercentage();
        this.minPoints = createMembershipTierCmd.getMinPoints();
        this.deleted = createMembershipTierCmd.getDefaultTier();
        this.defaultTier = createMembershipTierCmd.getDefaultTier();
    }

    public void update(CreateOrUpdateMembershipTierCmd updateMembershipTierCmd) {
        if (updateMembershipTierCmd.getName() != null) {
            this.name = updateMembershipTierCmd.getName();
        }

        if (updateMembershipTierCmd.getCashbackPercentage() != null) {
            this.cashbackPercentage = updateMembershipTierCmd.getCashbackPercentage();
        }

        if (updateMembershipTierCmd.getMinPoints() != null) {
            this.minPoints = updateMembershipTierCmd.getMinPoints();
        }

        if (updateMembershipTierCmd.getDefaultTier() != null) {
            this.defaultTier = updateMembershipTierCmd.getDefaultTier();
        }
    }
}
