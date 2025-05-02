package com.evo.cart.domain;

import com.evo.cart.domain.command.UpdateCartCmd;
import com.evo.cart.domain.command.CreateCartItemCmd;
import com.evo.common.Auditor;
import com.evo.common.enums.DiscountType;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Setter
@Getter
public class Cart extends Auditor {
    private UUID id;
    private UUID userId;
    List<CartItem> cartItems;

    public Cart(UpdateCartCmd updateCartCmd) {
        this.userId = updateCartCmd.getUserId();
        updateCartCmd.setUserId(this.id);
        if (this.cartItems == null) {
            this.cartItems = new ArrayList<>();
        }
        if (updateCartCmd.getCartItems() != null) {
            for (CreateCartItemCmd createCartItemCmd : updateCartCmd.getCartItems()) {
                CartItem cartItem = new CartItem(createCartItemCmd);
                this.cartItems.add(cartItem);
            }
        }
    }

    public void update(UpdateCartCmd updateCartCmd) {
        if (this.cartItems == null) {
            this.cartItems = new ArrayList<>();
        }
        if (updateCartCmd.getCartItems() == null) {
            return;
        }
        Map<UUID, CartItem> existingCartItemMap = this.cartItems.stream()
                .peek(ci -> ci.setDeleted(true))
                .collect(Collectors.toMap(CartItem::getProductVariantId, ci -> ci));
        List<CreateCartItemCmd> createCartItemCmds = updateCartCmd.getCartItems();
        for (CreateCartItemCmd createCartItemCmd : createCartItemCmds) {
            UUID productVariantId = createCartItemCmd.getProductVariantId();
            if (existingCartItemMap.containsKey(productVariantId)) {
                // Nếu đã tồn tại, cập nhật deleted = false
                existingCartItemMap.get(productVariantId).setDeleted(false);
                existingCartItemMap.get(productVariantId).setQuantity(createCartItemCmd.getQuantity());
            } else {
                createCartItemCmd.setCartId(this.id);
                CartItem newcartItem = new CartItem(createCartItemCmd);
                this.cartItems.add(newcartItem);
            }
        }
    }
}
