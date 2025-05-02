package com.evo.profile.infrastructure.persistence.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Table(name = "referrals")
public class ReferralEntity {
    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "referrer")
    private String referrer;

    @Column(name = "referee")
    private String referee;

    @Column(name = "first_order_completed")
    private Boolean firstOrderCompleted;

    @Column(name = "commissio`n_earned")
    private Long commissionEarned;
}
