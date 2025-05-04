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
@Setter
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
    private Double weight;
    private  Double height;
    private Double width;
    private Double length;

    public CartItem(CreateCartItemCmd createCartItemCmd) {
        this.productVariantId = createCartItemCmd.getProductVariantId();
        this.cartId = createCartItemCmd.getCartId();
        this.quantity = createCartItemCmd.getQuantity();
        this.productId = createCartItemCmd.getProductId();
        this.deleted = false;
    }
}
