package com.evo.product.application.dto.request;

import java.util.UUID;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrUpdateProductVariantRequest {
    private UUID id;
    private UUID productId;

    @NotBlank
    private String size;

    @NotBlank
    private String color;

    @Min(0)
    private int quantity;

    private String sku;
    private Boolean deleted;
}
