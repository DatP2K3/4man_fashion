package com.fourman.profile.application.dto.response;

import java.time.Instant;
import java.util.UUID;

import com.fourman.common.enums.CashbackTransactionType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CashbackTransactionDTO {
    private UUID id;
    private UUID userId;
    private UUID orderId;
    private Long amount;
    private CashbackTransactionType type;
    private String description;
    private Instant createdAt;
    private Instant updatedAt;
    private String createdBy;
}
