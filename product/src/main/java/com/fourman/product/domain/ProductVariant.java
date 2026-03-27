package com.fourman.product.domain;

import java.util.UUID;

import com.fourman.common.Auditor;
import com.fourman.product.domain.command.CreateOrUpdateProductVariantCmd;

import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
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

    public void markAsDeleted() {
        this.deleted = true;
    }

    public void restore() {
        this.deleted = false;
    }

    public void adjustQuantity(int delta) {
        this.quantity += delta;
    }
}
