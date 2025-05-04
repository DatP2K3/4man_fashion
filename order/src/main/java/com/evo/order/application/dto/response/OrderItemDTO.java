package com.evo.order.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {
    private UUID id;
    private UUID orderId;
    private UUID productId;
    private UUID productVariantId;
    private int quantity;
    private Long price;
}
