package com.evo.profile.infrastructure.persistence.entity;

import java.util.UUID;

import jakarta.persistence.*;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.evo.common.entity.AuditEntity;
import com.evo.common.enums.MembershipTierType;

import lombok.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(callSuper = false)
@Table(name = "membership_tiers")
public class MembershipTierEntity extends AuditEntity {
    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    @Enumerated(EnumType.STRING)
    private MembershipTierType name;

    @Column(name = "cashback_percentage")
    private Double cashbackPercentage;

    @Column(name = "min_points")
    private Integer minPoints;

    @Column(name = "deleted")
    private boolean deleted;

    @Column(name = "default_tier")
    private boolean defaultTier;
}
