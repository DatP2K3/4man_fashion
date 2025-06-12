package com.evo.product.domain.command;

import com.evo.common.dto.event.ProductVariantSync;
import com.evo.common.enums.OperationType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class UpdateProductVariantQuantityCmd {
    private UUID id;
    private UUID productId;
    private int totalQuantity;
    private OperationType operationType;
}
