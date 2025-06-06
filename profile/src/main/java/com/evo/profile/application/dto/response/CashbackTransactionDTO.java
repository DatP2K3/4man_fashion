package com.evo.profile.application.dto.response;

import java.util.Date;
import java.util.UUID;

import com.evo.common.enums.CashbackTransactionType;
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
    private Date createdAt;
    private Date updatedAt;
    private String createdBy;
}
