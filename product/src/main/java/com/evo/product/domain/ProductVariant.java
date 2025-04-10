package com.evo.product.domain;

import com.evo.product.domain.command.CreateOrUpdateProductVariantCmd;
import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Setter
@Getter
public class ProductVariant {
    private UUID id;
    private UUID productId;
    private String size;
    private String color;
    private int quantity;
    private String sku;

    public ProductVariant(CreateOrUpdateProductVariantCmd cmd) {
        this.id = cmd.getId();
        this.productId = cmd.getProductId();
        this.size = cmd.getSize();
        this.color = cmd.getColor();
        this.quantity = cmd.getQuantity();
        this.sku = cmd.getSku();
    }
}
