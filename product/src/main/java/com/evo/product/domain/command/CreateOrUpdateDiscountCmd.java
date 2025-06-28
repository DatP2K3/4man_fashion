package com.evo.product.domain.command;

import java.time.Instant;
import java.util.UUID;

import com.evo.common.enums.DiscountType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOrUpdateDiscountCmd {
    private UUID id;
    private UUID productId;
    private String name;
    private Instant startDate;
    private Instant endDate;
    private DiscountType discountType;
    private Integer discountPercentage;
    private Long discountPrice;
}
