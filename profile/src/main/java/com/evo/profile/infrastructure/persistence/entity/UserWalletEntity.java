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
    private UUID id;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "cashback_balance")
    private Long cashbackBalance;

    @Column(name = "total_points")
    private Integer totalPoints;
}
