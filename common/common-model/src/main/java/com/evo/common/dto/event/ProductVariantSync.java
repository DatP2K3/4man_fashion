package com.evo.common.dto.event;

import java.util.UUID;

import com.evo.common.enums.OperationType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductVariantSync {
    private UUID id;
    private UUID productId;
    private int totalQuantity;
    private OperationType operationType;
}
