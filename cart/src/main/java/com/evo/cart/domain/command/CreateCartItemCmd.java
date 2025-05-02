package com.evo.cart.domain.command;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateCartItemCmd {
    private UUID id;
    private UUID productId;
    private UUID productVariantId;
    private UUID cartId;
    private Integer quantity;
    private Long price;
    private Boolean deleted;
}
