package com.evo.product.application.dto.response;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.evo.common.enums.DiscountType;
import com.evo.product.domain.ProductImage;
import com.evo.product.domain.ProductVariant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private UUID id;
    private String name;
    private Long originPrice;
    private Long discountPrice;
    private Integer discountPercent;
    private DiscountType discountType;
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
    private UUID avatarId;
}
