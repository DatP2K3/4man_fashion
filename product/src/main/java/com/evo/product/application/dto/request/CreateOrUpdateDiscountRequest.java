package com.evo.product.application.dto.request;

import java.time.Instant;
import java.util.UUID;

import com.evo.common.enums.DiscountStatus;
import com.evo.common.enums.DiscountType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrUpdateDiscountRequest {
    private UUID id;
    private UUID productId;
    private String name;
    private Instant startDate;
    private Instant endDate;
    private DiscountType discountType;
    private Integer discountPercentage;
    private Long discountPrice;
}
