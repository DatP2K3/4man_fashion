package com.evo.order.domain.command;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateOrderCmd {
    private UUID orderId;
    private UUID productId;
    private UUID productVariantId;
    private int quantity;
    private Long price;
}
