package com.evo.product.domain.command;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOrUpdateProductCmd {
    private UUID id;
    private String name;
    private Long originPrice;
    private UUID categoryId;
    private Map<String, String> description;
    private String introduce; // Introduce is a short description of the product(html)
    private int weight;
    private int length;
    private int width;
    private Long totalSold;
    private BigDecimal averageRating;
    private int height;
    private Boolean hidden;
    List<CreateOrUpdateProductVariantCmd> productVariants;
    List<CreateOrUpdateProductImageCmd> productImages;
    List<CreateOrUpdateDiscountCmd> discounts;
}
