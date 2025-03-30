package com.evo.product.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.UUID;

@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Setter
@Getter
public class Discount {
    private UUID id;
    private UUID productId;
    private Instant startDate;
    private Instant endDate;
    private String status;
    private int priority;
    private int percentage;
    private double discountPrice;
    private int discountQuantity;
}
