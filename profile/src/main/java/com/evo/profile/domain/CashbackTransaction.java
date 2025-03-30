package com.evo.profile.domain;

import java.util.UUID;

import com.evo.profile.domain.enums.CashbackTransactionType;

import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Setter
@Getter
public class CashbackTransaction {
    private UUID id;
    private UUID userId;
    private UUID orderId;
    private Long amount;
    private CashbackTransactionType type;
    private String description;
}
