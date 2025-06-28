package com.evo.order.domain.command;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOrderItemCmd {
    private UUID orderId;
    private UUID productId;
    private UUID productVariantId;
    private String name;
    private int quantity;
    private Long price;
    private int weight;
    private int height;
    private int width;
    private int length;
}
