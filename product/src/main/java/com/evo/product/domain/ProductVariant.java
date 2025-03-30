package com.evo.product.domain;

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

}
