package com.fourman.profile.application.dto.request;

import java.util.UUID;

import com.fourman.common.enums.CashbackTransactionType;

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
