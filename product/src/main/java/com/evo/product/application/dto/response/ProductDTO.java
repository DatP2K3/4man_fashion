package com.evo.product.application.dto.response;

import com.evo.product.domain.ProductImage;
import com.evo.product.domain.ProductVariant;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ProductDTO {
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
    List<ProductVariant> productVariants;
    List<ProductImage> productImages;
}
