package com.evo.product.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrUpdateProductVariantRequest {
    private UUID id;
    private UUID productId;
    private String size;
    private String color;
    private int quantity;
    private String sku;
}
