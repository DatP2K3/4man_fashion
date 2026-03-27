package com.evo.product.application.dto.request;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import com.evo.product.domain.Discount;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrUpdateProductRequest {
    private UUID id;

    @NotBlank
    private String name;

    @NotNull
    @Min(0)
    private Long originPrice;

    @NotNull
    private UUID categoryId;

    private Map<String, String> description;
    private String introduce;

    @Min(0)
    private int weight;

    @Min(0)
    private int length;

    @Min(0)
    private int width;

    private Long totalSold;
    private BigDecimal averageRating;

    @Min(0)
    private int height;

    private Boolean hidden;

    @Valid
    List<Discount> discounts;

    @Valid
    List<CreateOrUpdateProductVariantRequest> productVariants;

    List<CreateOrUpdateProductImageRequest> productImages;
}
