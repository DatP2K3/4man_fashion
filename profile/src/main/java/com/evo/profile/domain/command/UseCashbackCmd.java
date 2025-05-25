package com.evo.profile.domain.command;

import java.util.UUID;

import com.evo.common.enums.CashbackTransactionType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UseCashbackCmd {
    private UUID userId;
    private UUID orderId;
    private Long amount;
    private String description;
    private CashbackTransactionType type;
}
