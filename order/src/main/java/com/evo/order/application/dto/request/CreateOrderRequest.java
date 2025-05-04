package com.evo.order.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderRequest {
    private UUID orderId;
    private UUID productId;
    private UUID productVariantId;
    private int quantity;
    private Long price;
}
