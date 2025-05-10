package com.evo.product.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import com.evo.common.Auditor;
import com.evo.common.enums.DiscountStatus;
import com.evo.common.enums.DiscountType;
import com.evo.product.domain.command.CreateOrUpdateDiscountCmd;
import com.evo.product.domain.command.CreateOrUpdateProductCmd;
import com.evo.product.domain.command.CreateOrUpdateProductImageCmd;
import com.evo.product.domain.command.CreateOrUpdateProductVariantCmd;
import com.evo.product.infrastructure.support.IdUtils;
import com.evo.product.infrastructure.support.exception.AppErrorCode;
import com.evo.product.infrastructure.support.exception.AppException;

import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Setter
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
    private String introduce; // Introduce is a short description of the product(html)
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
                .peek(rp -> rp.setDeleted(true))
                .collect(Collectors.toMap(ProductVariant::getId, rp -> rp));

        for (CreateOrUpdateProductVariantCmd productVariantCmd : productVariantCmds) {
            UUID productVariantId = productVariantCmd.getId();
            if (existingProductVariantMap.containsKey(productVariantId)) {
                existingProductVariantMap.get(productVariantId).setDeleted(false);
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
        // Tạo map chứa rolePermission hiện tại và tạm thời xoá mềm
        Map<UUID, ProductImage> existingProductImageMap = this.productImages.stream()
                .peek(rp -> rp.setDeleted(true))
                .collect(Collectors.toMap(ProductImage::getId, rp -> rp));

        for (CreateOrUpdateProductImageCmd productImageCmd : productImageCmds) {
            UUID productImageId = productImageCmd.getId();
            if (existingProductImageMap.containsKey(productImageId)) {
                // Nếu đã tồn tại, cập nhật deleted = false
                existingProductImageMap.get(productImageId).setDeleted(false);
            } else {
                // Nếu chưa tồn tại, tạo mới RolePermission
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
                throw new AppException(AppErrorCode.PROMOTION_TYPE_IS_EXIST);
            }
        });

        Discount discount = new Discount(createOrUpdateDiscountCmd);
        this.discounts.add(discount);
    }

    public void updateDiscount(CreateOrUpdateDiscountCmd createOrUpdateDiscountCmd) {
        for (Discount discount : this.discounts) {
            if (discount.getId().equals(createOrUpdateDiscountCmd.getId())) {

                if (createOrUpdateDiscountCmd.getName() != null) {
                    discount.setName(createOrUpdateDiscountCmd.getName());
                }

                if (createOrUpdateDiscountCmd.getDiscountPrice() != null) {
                    discount.setDiscountPrice(createOrUpdateDiscountCmd.getDiscountPrice());
                }

                if (createOrUpdateDiscountCmd.getDiscountPercentage() != null) {
                    discount.setDiscountPercentage(createOrUpdateDiscountCmd.getDiscountPercentage());
                }

                if (createOrUpdateDiscountCmd.getDiscountPrice() != null) {
                    discount.setDiscountPrice(createOrUpdateDiscountCmd.getDiscountPrice());
                }

                if (createOrUpdateDiscountCmd.getStartDate() != null) {
                    discount.setStartDate(createOrUpdateDiscountCmd.getStartDate());
                }

                if (createOrUpdateDiscountCmd.getEndDate() != null) {
                    discount.setEndDate(createOrUpdateDiscountCmd.getEndDate());
                }

                Instant startDate = createOrUpdateDiscountCmd.getStartDate();
                Instant endDate = createOrUpdateDiscountCmd.getEndDate();

                if (endDate.isBefore(Instant.now())) {
                    discount.setStatus(DiscountStatus.EXPIRED);
                } else if (startDate.isBefore(Instant.now()) && Instant.now().isBefore(endDate)) {
                    discount.setStatus(DiscountStatus.ACTIVE);
                } else if (startDate.isAfter(Instant.now())) {
                    discount.setStatus(DiscountStatus.SCHEDULED);
                }
            }
        }
    }

    public void enrichDiscountInfo() {
        if (this.discounts != null && !this.discounts.isEmpty()) {
            for (Discount discount : this.discounts) {
                if (discount.getStatus() == DiscountStatus.ACTIVE) {
                    if (discount.getDiscountPrice() != null && discount.getDiscountPrice() > 0) {
                        this.caculateByDiscountPrice(discount.getDiscountPrice());
                    } else if (discount.getDiscountPercentage() != null && discount.getDiscountPercentage() > 0) {
                        this.caculateByDiscountPercent(discount.getDiscountPercentage());
                    } else {
                        throw new AppException(AppErrorCode.DISCOUNT_PRICE_OR_PERCENT_IS_REQUIRED);
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

    private void caculateByDiscountPrice(Long discountPrice) {
        this.discountPrice = discountPrice;
        this.discountPercent = (int) (discountPrice / this.originPrice * 100);
    }

    private void caculateByDiscountPercent(Integer discountPercent) {
        this.discountPrice = (long) (this.originPrice * (100 - discountPercent) / 100);
        this.discountPercent = discountPercent;
    }
}
