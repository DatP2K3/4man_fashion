package com.evo.cart.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {
    private UUID id;
    private UUID userId;
    List<CartItemDTO> cartItems;
}
