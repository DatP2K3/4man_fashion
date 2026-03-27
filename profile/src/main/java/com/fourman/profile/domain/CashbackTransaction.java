package com.fourman.profile.domain;

import java.util.UUID;

import com.fourman.common.Auditor;
import com.fourman.common.enums.CashbackTransactionType;
import com.fourman.profile.domain.command.ProcessCashbackCmd;
import com.fourman.profile.domain.command.UseCashbackCmd;

import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Setter
@Getter
public class CashbackTransaction extends Auditor {
    private UUID id;
    private UUID userId;
    private UUID orderId;
    private Long amount;
    private CashbackTransactionType type;
    private String description;

    public CashbackTransaction(ProcessCashbackCmd cmd) {
        this.userId = cmd.getUserId();
        this.orderId = cmd.getOrderId();
        this.amount = cmd.getCashbackAmount();
        this.type = cmd.getType();
        this.description = cmd.getDescription();
    }

    public CashbackTransaction(UseCashbackCmd cmd) {
        this.userId = cmd.getUserId();
        this.orderId = cmd.getOrderId();
        this.amount = cmd.getAmount();
        this.type = cmd.getType();
        this.description = cmd.getDescription();
    }
}
