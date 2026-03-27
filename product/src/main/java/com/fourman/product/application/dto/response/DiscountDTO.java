package com.fourman.product.application.dto.response;

import java.time.Instant;
import java.util.UUID;

import com.fourman.common.enums.DiscountStatus;
import com.fourman.common.enums.DiscountType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiscountDTO {
    private String name;
    private UUID id;
    private UUID productId;
    private Instant startDate;
    private Instant endDate;
    private DiscountStatus status;
    private DiscountType discountType;
    private int discountPercentage;
    private double discountPrice;
}
