package com.evo.product.application.dto.request;

import java.time.Instant;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import com.evo.common.enums.DiscountType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrUpdateDiscountRequest {
    private UUID id;

    @NotNull
    private UUID productId;

    @NotBlank
    private String name;

    @NotNull
    private Instant startDate;

    @NotNull
    private Instant endDate;

    @NotNull
    private DiscountType discountType;

    private Integer discountPercentage;
    private Long discountPrice;
}
