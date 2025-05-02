package com.evo.product.domain.command;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOrUpdateProductVariantCmd {
    private UUID id;
    private UUID productId;
    private String size;
    private String color;
    private int quantity;
    private String sku;
    private Boolean deleted;
}
