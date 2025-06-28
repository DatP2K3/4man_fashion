package com.evo.common.dto.event;

import java.util.UUID;

import com.evo.common.enums.CashbackTransactionType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProcessCashbackEvent {
    private UUID userId;
    private UUID orderId;
    private Long orderAmount;
    private CashbackTransactionType type;
}
