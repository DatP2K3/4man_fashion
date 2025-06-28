package com.evo.cart.application.dto.request;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCartRequest {
    private UUID id;
    private UUID userId;
    List<CreateCartItemRequest> cartItems;
    private Boolean deleted;
}
