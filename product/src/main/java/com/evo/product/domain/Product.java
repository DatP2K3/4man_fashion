package com.evo.product.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import com.evo.common.Auditor;
import com.evo.common.enums.DiscountStatus;
import com.evo.common.enums.DiscountType;
import com.evo.common.enums.OperationType;
import com.evo.common.exception.ResponseException;
import com.evo.product.domain.command.CreateOrUpdateDiscountCmd;
import com.evo.product.domain.command.CreateOrUpdateProductCmd;
import com.evo.product.domain.command.CreateOrUpdateProductImageCmd;
import com.evo.product.domain.command.CreateOrUpdateProductVariantCmd;
import com.evo.product.domain.command.UpdateProductVariantQuantityCmd;
import com.evo.product.infrastructure.support.IdUtils;
import com.evo.product.infrastructure.support.exception.BadRequestError;
import com.evo.product.infrastructure.support.exception.NotFoundError;

import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
public class Product extends Auditor {
    private UUID id;
    private String name;
    private Long originPrice;
    private Long discountPrice;
    private Integer discountPercent;
    private DiscountType discountType;
    private UUID categoryId;
    private Map<String, String> description;
    private String introduce;
    private int weight;
    private int length;
    private int width;
    private int height;
    private Boolean hidden;
    private Long totalSold;
    private BigDecimal averageRating;
    List<ProductVariant> productVariants;
    List<ProductImage> productImages;
    List<Discount> discounts;

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
        this.discounts = new ArrayList<>();

        createOrUpdateProductVariant(createOrUpdateProductCmd.getProductVariants());
        createOrUpdateProductImage(createOrUpdateProductCmd.getProductImages());
    }

    public void updateProductVariantQuantity(UpdateProductVariantQuantityCmd cmd) {
        if (this.productVariants == null) {
            throw new ResponseException(NotFoundError.PRODUCT_VARIANT_NOT_FOUND);
        }
        for (ProductVariant productVariant : this.productVariants) {
            if (productVariant.getId().equals(cmd.getId())) {
                if (cmd.getOperationType() == null) {
                    throw new ResponseException(BadRequestError.OPERATION_TYPE_IS_REQUIRED);
                }
                if (cmd.getOperationType().equals(OperationType.INCREASE)) {
                    productVariant.adjustQuantity(cmd.getTotalQuantity());
                } else if (cmd.getOperationType().equals(OperationType.DECREASE)) {
                    productVariant.adjustQuantity(-cmd.getTotalQuantity());
                } else {
                    throw new ResponseException(BadRequestError.INVALID_OPERATION_TYPE);
                }
                return;
            }
        }
        throw new ResponseException(NotFoundError.PRODUCT_VARIANT_NOT_FOUND);
    }

    public void update(CreateOrUpdateProductCmd createOrUpdateProductCmd) {
        if (createOrUpdateProductCmd.getName() != null) {
            this.name = createOrUpdateProductCmd.getName();
        }
        if (createOrUpdateProductCmd.getOriginPrice() != null) {
            this.originPrice = createOrUpdateProductCmd.getOriginPrice();
        }
        if (createOrUpdateProductCmd.getCategoryId() != null) {
            this.categoryId = createOrUpdateProductCmd.getCategoryId();
        }
        if (createOrUpdateProductCmd.getDescription() != null) {
            this.description = createOrUpdateProductCmd.getDescription();
        }
        if (createOrUpdateProductCmd.getIntroduce() != null) {
            this.introduce = createOrUpdateProductCmd.getIntroduce();
        }
        if (createOrUpdateProductCmd.getWeight() != 0) {
            this.weight = createOrUpdateProductCmd.getWeight();
        }
        if (createOrUpdateProductCmd.getLength() != 0) {
            this.length = createOrUpdateProductCmd.getLength();
        }
        if (createOrUpdateProductCmd.getWidth() != 0) {
            this.width = createOrUpdateProductCmd.getWidth();
        }
        if (createOrUpdateProductCmd.getHeight() != 0) {
            this.height = createOrUpdateProductCmd.getHeight();
        }
        if (createOrUpdateProductCmd.getHidden() != null) {
            this.hidden = createOrUpdateProductCmd.getHidden();
        }
        createOrUpdateProductVariant(createOrUpdateProductCmd.getProductVariants());
        createOrUpdateProductImage(createOrUpdateProductCmd.getProductImages());
    }

    private void createOrUpdateProductVariant(List<CreateOrUpdateProductVariantCmd> productVariantCmds) {
        if (this.productVariants == null) {
            this.productVariants = new ArrayList<>();
        }
        Map<UUID, ProductVariant> existingProductVariantMap = this.productVariants.stream()
                .peek(ProductVariant::markAsDeleted)
                .collect(Collectors.toMap(ProductVariant::getId, rp -> rp));

        for (CreateOrUpdateProductVariantCmd productVariantCmd : productVariantCmds) {
            UUID productVariantId = productVariantCmd.getId();
            if (existingProductVariantMap.containsKey(productVariantId)) {
                existingProductVariantMap.get(productVariantId).restore();
            } else {
                productVariantCmd.setProductId(this.id);
                ProductVariant newProductVariant = new ProductVariant(productVariantCmd);
                this.productVariants.add(newProductVariant);
            }
        }
    }

    private void createOrUpdateProductImage(List<CreateOrUpdateProductImageCmd> productImageCmds) {
        if (this.productImages == null) {
            this.productImages = new ArrayList<>();
        }
        Map<UUID, ProductImage> existingProductImageMap = this.productImages.stream()
                .peek(ProductImage::markAsDeleted)
                .collect(Collectors.toMap(ProductImage::getId, rp -> rp));

        for (CreateOrUpdateProductImageCmd productImageCmd : productImageCmds) {
            UUID productImageId = productImageCmd.getId();
            if (existingProductImageMap.containsKey(productImageId)) {
                existingProductImageMap.get(productImageId).restore();
            } else {
                productImageCmd.setProductId(this.id);
                ProductImage newProductImage = new ProductImage(productImageCmd);
                this.productImages.add(newProductImage);
            }
        }
    }

    public void createDiscount(CreateOrUpdateDiscountCmd createOrUpdateDiscountCmd) {
        if (this.discounts == null) {
            this.discounts = new ArrayList<>();
        }
        this.discounts.forEach(discount -> {
            if (discount.getDiscountType().equals(createOrUpdateDiscountCmd.getDiscountType())) {
                throw new ResponseException(BadRequestError.PROMOTION_TYPE_IS_EXIST);
            }
        });

        Discount discount = new Discount(createOrUpdateDiscountCmd);
        this.discounts.add(discount);
    }

    public void updateDiscount(CreateOrUpdateDiscountCmd createOrUpdateDiscountCmd) {
        for (Discount discount : this.discounts) {
            if (discount.getId().equals(createOrUpdateDiscountCmd.getId())) {
                discount.updateFrom(createOrUpdateDiscountCmd);
            }
        }
    }

    public void enrichDiscountInfo() {
        if (this.discounts != null && !this.discounts.isEmpty()) {
            for (Discount discount : this.discounts) {
                if (discount.getStatus() == DiscountStatus.ACTIVE) {
                    if (discount.getDiscountPrice() != null && discount.getDiscountPrice() > 0) {
                        this.calculateByDiscountPrice(discount.getDiscountPrice());
                    } else if (discount.getDiscountPercentage() != null && discount.getDiscountPercentage() > 0) {
                        this.calculateByDiscountPercent(discount.getDiscountPercentage());
                    } else {
                        throw new ResponseException(BadRequestError.DISCOUNT_PRICE_OR_PERCENT_IS_REQUIRED);
                    }
                    if (discount.getDiscountType() != null) {
                        this.discountType = discount.getDiscountType();
                    }
                    if (this.discountType == DiscountType.FLASH_SALE) {
                        return;
                    }
                }
            }
        } else {
            this.discountPrice = null;
            this.discountPercent = null;
            this.discountType = null;
        }
    }

    private void calculateByDiscountPrice(Long discountPrice) {
        this.discountPrice = discountPrice;
        this.discountPercent = (int) (discountPrice / this.originPrice * 100);
    }

    private void calculateByDiscountPercent(Integer discountPercent) {
        this.discountPrice = (long) (this.originPrice * (100 - discountPercent) / 100);
        this.discountPercent = discountPercent;
    }

    public void toggleVisibility() {
        if (this.hidden == null) {
            this.hidden = false;
        }
        this.hidden = !this.hidden;
    }

    /**
     * Used by infrastructure layer to enrich product with its variants after loading from DB.
     */
    public void enrichProductVariants(List<ProductVariant> productVariants) {
        this.productVariants = productVariants;
    }

    /**
     * Used by infrastructure layer to enrich product with its images after loading from DB.
     */
    public void enrichProductImages(List<ProductImage> productImages) {
        this.productImages = productImages;
    }

    /**
     * Used by infrastructure layer to enrich product with its discounts after loading from DB.
     */
    public void enrichDiscounts(List<Discount> discounts) {
        this.discounts = discounts;
    }
}
