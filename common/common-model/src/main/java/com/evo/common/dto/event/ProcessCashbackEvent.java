package com.evo.common.dto.event;

import com.evo.common.enums.CashbackTransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

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
