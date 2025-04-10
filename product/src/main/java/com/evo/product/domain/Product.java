package com.evo.product.domain;

import com.evo.product.domain.command.CreateOrUpdateProductCmd;
import com.evo.product.domain.command.CreateOrUpdateProductImageCmd;
import com.evo.product.domain.command.CreateOrUpdateProductVariantCmd;
import com.evo.product.infrastructure.support.IdUtils;
import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.util.ArrayList;
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
    private String introduce; // Introduce is a short description of the product(html)
    private Double weight;
    private Double length;
    private Double width;
    private Double height;
    private Boolean hidden;
    private Long totalSold;
    private BigDecimal averageRating;
    List<ProductVariant> productVariants;
    List<ProductImage> productImages;

    public Product(CreateOrUpdateProductCmd createOrUpdateProductCmd) {
        this.id = IdUtils.nextId();
        this.name = createOrUpdateProductCmd.getName();
        this.originPrice = createOrUpdateProductCmd.getOriginPrice();
        this.categoryId = createOrUpdateProductCmd.getCategoryId();
        this.description = createOrUpdateProductCmd.getDescription();
        this.introduce = createOrUpdateProductCmd.getIntroduce();
        this.weight = createOrUpdateProductCmd.getWeight();
        this.length = createOrUpdateProductCmd.getLength();
        this.width = createOrUpdateProductCmd.getWidth();
        this.height = createOrUpdateProductCmd.getHeight();
        this.hidden = createOrUpdateProductCmd.getHidden();
        this.totalSold = 0L;
        this.averageRating = BigDecimal.valueOf(0);

        this.productVariants = new ArrayList<>();
        if (createOrUpdateProductCmd.getProductVariants() != null) {
            for (CreateOrUpdateProductVariantCmd productVariantCmd : createOrUpdateProductCmd.getProductVariants()) {
                ProductVariant productVariant = new ProductVariant(productVariantCmd);
                productVariant.setProductId(this.id);
                this.productVariants.add(productVariant);
            }
        }

        this.productImages = new ArrayList<>();
        if (createOrUpdateProductCmd.getProductImages() != null) {
            for (CreateOrUpdateProductImageCmd productImageCmd : createOrUpdateProductCmd.getProductImages()) {
                ProductImage productImage = new ProductImage(productImageCmd);
                productImage.setProductId(this.id);
                this.productImages.add(productImage);
            }
        }
    }
}
