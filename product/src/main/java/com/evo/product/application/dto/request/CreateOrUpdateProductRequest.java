package com.evo.product.application.dto.request;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.evo.product.domain.Discount;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrUpdateProductRequest {
    private UUID id;
    private String name;
    private Long originPrice;
    private UUID categoryId;
    private Map<String, String> description;
    private String introduce; // Introduce is a short description of the product(html)
    private Double weight;
    private Double length;
    private Double width;
    private Long totalSold;
    private BigDecimal averageRating;
    private Double height;
    private Boolean hidden;
    List<Discount> discounts;
    List<CreateOrUpdateProductVariantRequest> productVariants;
    List<CreateOrUpdateProductImageRequest> productImages;
}
