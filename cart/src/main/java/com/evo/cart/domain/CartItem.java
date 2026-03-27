package com.evo.cart.domain;

import java.util.UUID;

import com.evo.cart.domain.command.CreateCartItemCmd;
import com.evo.common.Auditor;
import com.evo.common.enums.DiscountType;

import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
public class CartItem extends Auditor {
    private UUID id;
    private UUID productId;
    private UUID productVariantId;
    private UUID cartId;
    private Integer quantity;
    private Boolean deleted;
    private String name;
    private Long originPrice;
    private Long discountPrice;
    private Integer discountPercent;
    private DiscountType discountType;
    private UUID avatarId;
    private String size;
    private String color;
    private int weight;
    private int height;
    private int width;
    private int length;

    public CartItem(CreateCartItemCmd createCartItemCmd) {
        this.productVariantId = createCartItemCmd.getProductVariantId();
        this.cartId = createCartItemCmd.getCartId();
        this.quantity = createCartItemCmd.getQuantity();
        this.productId = createCartItemCmd.getProductId();
        this.deleted = false;
    }

    public void markAsDeleted() {
        this.deleted = true;
    }

    public void restoreWithQuantity(int quantity) {
        this.deleted = false;
        this.quantity = quantity;
    }

    /**
     * Enrich cart item with product information from product service.
     */
    public void enrichFromProduct(
            String name,
            UUID avatarId,
            Integer discountPercent,
            Long discountPrice,
            DiscountType discountType,
            Long originPrice,
            int height,
            int width,
            int length,
            int weight) {
        this.name = name;
        this.avatarId = avatarId;
        this.discountPercent = discountPercent;
        this.discountPrice = discountPrice;
        this.discountType = discountType;
        this.originPrice = originPrice;
        this.height = height;
        this.width = width;
        this.length = length;
        this.weight = weight;
    }

    /**
     * Enrich variant info (size, color).
     */
    public void enrichVariantInfo(String size, String color) {
        this.size = size;
        this.color = color;
    }
}
