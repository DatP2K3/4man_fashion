package com.evo.cart.application.service;

import com.evo.cart.application.dto.request.UpdateCartRequest;
import com.evo.common.dto.response.CartDTO;

import java.util.UUID;

public interface CartCommandService {
    CartDTO getOrInitCart();

    CartDTO updateCart(UpdateCartRequest request);

    void emptyCart(UUID cartId);
}
