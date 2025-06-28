package com.evo.profile.domain.command;

import java.util.UUID;

import com.evo.common.enums.CashbackTransactionType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProcessCashbackCmd {
    private UUID userId;
    private UUID orderId;
    private Long orderAmount;
    private String description;
    private Long cashbackAmount;
    private CashbackTransactionType type;
}
