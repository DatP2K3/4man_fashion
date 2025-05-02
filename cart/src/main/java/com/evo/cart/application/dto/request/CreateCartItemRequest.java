package com.evo.cart.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCartItemRequest {
    private UUID id;
    private UUID productId;
    private UUID productVariantId;
    private UUID cartId;
    private Integer quantity;
    private Long price;
    private Boolean deleted;
}
