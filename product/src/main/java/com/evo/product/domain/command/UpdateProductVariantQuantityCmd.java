package com.evo.product.domain.command;

import java.util.UUID;

import com.evo.common.enums.OperationType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProductVariantQuantityCmd {
    private UUID id;
    private UUID productId;
    private int totalQuantity;
    private OperationType operationType;
}
