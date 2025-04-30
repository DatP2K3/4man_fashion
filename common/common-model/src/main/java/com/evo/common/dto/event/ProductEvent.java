package com.evo.common.dto.event;

import java.math.BigDecimal;
import java.util.UUID;

import com.evo.common.enums.DiscountType;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductEvent {
    private UUID id;
    private String name;
    private Long originPrice;
    private Long discountPrice;
    private Integer discountPercent;
    private DiscountType discountType;
    private UUID categoryId;
    private Long totalSold;
    private BigDecimal averageRating;
    private Boolean hidden;
    private UUID avatarId;
}
