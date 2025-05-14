package com.evo.order.application.dto.request;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderItemRequest {
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
