package com.evo.profile.application.dto.request;

import java.util.UUID;

import com.evo.common.enums.CashbackTransactionType;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProcessCashbackRequest {
    private UUID userId;
    private UUID orderId;
    private Long orderAmount;
    private CashbackTransactionType type;
}
