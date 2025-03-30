package com.evo.product.domain;

import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.UUID;

@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Setter
@Getter
public class ProductStatistics {
    private UUID id;
    private UUID productId;
    private Long totalSold;
    private BigDecimal averageRating;
}
