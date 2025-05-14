package com.evo.cart.application.service;

import java.util.UUID;

import com.evo.cart.application.dto.request.UpdateCartRequest;
import com.evo.common.dto.response.CartDTO;

public interface CartCommandService {
    CartDTO getOrInitCart();

    CartDTO updateCart(UpdateCartRequest request);

    void emptyCart(UUID cartId);
}
