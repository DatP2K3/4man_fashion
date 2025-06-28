package com.evo.profile.infrastructure.persistence.entity;

import java.util.UUID;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Table(name = "user_wallets")
public class UserWalletEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "user_id")
    private UUID profileId;

    @Column(name = "cashback_balance")
    private Long cashbackBalance;

    @Column(name = "total_points")
    private Integer totalPoints;
}
