package com.evo.product.domain.command;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateOrUpdateProductVariantCmd {
    private UUID id;
    private UUID productId;
    private String size;
    private String color;
    private int quantity;
    private String sku;
}
