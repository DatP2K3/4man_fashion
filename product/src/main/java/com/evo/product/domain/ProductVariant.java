package com.evo.product.domain;

import java.util.UUID;

import com.evo.common.Auditor;
import com.evo.product.domain.command.CreateOrUpdateProductVariantCmd;

import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Setter
@Getter
public class ProductVariant extends Auditor {
    private UUID id;
    private UUID productId;
    private String size;
    private String color;
    private int quantity;
    private String sku;
    private Boolean deleted;

    public ProductVariant(CreateOrUpdateProductVariantCmd cmd) {
        if (cmd.getId() != null) {
            this.id = cmd.getId();
        }
        this.productId = cmd.getProductId();
        this.size = cmd.getSize();
        this.color = cmd.getColor();
        this.quantity = cmd.getQuantity();
        this.sku = cmd.getSku();
        this.deleted = false;
    }
}
