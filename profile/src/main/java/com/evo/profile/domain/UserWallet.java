package com.evo.profile.domain;

import java.util.UUID;

import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Setter
@Getter
public class UserWallet {
    private UUID id;
    private UUID profileId;
    private Long cashbackBalance;
    private Long totalPoints;
    private Long totalCoins;

    public UserWallet(UUID profileId) {
        this.profileId = profileId;
        this.cashbackBalance = 0L;
        this.totalPoints = 0L;
        this.totalCoins = 0L;
    }
}
