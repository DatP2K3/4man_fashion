package com.evo.profile.domain;

import java.util.UUID;

import com.evo.profile.infrastructure.support.exception.AppErrorCode;
import com.evo.profile.infrastructure.support.exception.AppException;

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

    public UserWallet(UUID profileId) {
        this.profileId = profileId;
        this.cashbackBalance = 0L;
        this.totalPoints = 0L;
    }

    public void plusCashbackBalance(Long amount) {
        if (amount < 0) {
            throw new AppException(AppErrorCode.INVALID_CASHBACK_AMOUNT);
        }
        this.totalPoints = totalPoints + amount / 1000;
        this.cashbackBalance += amount;
    }

    public void minusCashbackBalance(Long amount) {
        if (amount < 0 || amount > this.cashbackBalance) {
            throw new AppException(AppErrorCode.INVALID_CASHBACK_AMOUNT);
        }
        this.cashbackBalance -= amount;
    }
}
