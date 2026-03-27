package com.evo.cart.application.dto.request;

import java.util.UUID;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCartItemRequest {
    private UUID id;

    @NotNull
    private UUID productId;

    @NotNull
    private UUID productVariantId;

    private UUID cartId;

    @NotNull
    @Min(1)
    private Integer quantity;

    private Long price;
    private Boolean deleted;
}
