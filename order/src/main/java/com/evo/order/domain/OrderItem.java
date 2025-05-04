package com.evo.order.domain;

import com.evo.common.Auditor;
import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Setter
@Getter
public class OrderItem extends Auditor {
    private UUID id;
    private UUID orderId;
    private UUID productId;
    private UUID productVariantId;
    private int quantity;
    private Long price;
}
