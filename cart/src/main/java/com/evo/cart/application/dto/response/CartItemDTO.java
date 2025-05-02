package com.evo.cart.application.dto.response;

import com.evo.common.enums.DiscountType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDTO {
    private UUID id;
    private UUID productId;
    private UUID productVariantId;
    private UUID cartId;
    private Integer quantity;
    private String name;
    private Long originPrice;
    private Long discountPrice;
    private Integer discountPercent;
    private DiscountType discountType;
    private UUID avatarId;
    private String size;
    private String color;
    private Boolean deleted;
}
