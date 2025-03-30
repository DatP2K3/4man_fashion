package com.evo.product.domain;

import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Setter
@Getter
public class Product {
    private UUID id;
    private String name;
    private Long originPrice;
    private UUID categoryId;
    private Map<String, String> description;
    private Double weight;
    private Double length;
    private Double width;
    private Double height;
    private Boolean hidden;
    List<ProductVariant> productVariants;
    List<ProductImage> productImages;
    ProductStatistics productStatistics;
}
