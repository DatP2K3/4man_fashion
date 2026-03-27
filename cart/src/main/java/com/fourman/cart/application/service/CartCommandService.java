package com.fourman.cart.application.service;

import java.util.UUID;

import com.fourman.cart.application.dto.request.UpdateCartRequest;
import com.fourman.common.dto.response.CartDTO;

public interface CartCommandService {
    CartDTO getOrInitCart();

    CartDTO updateCart(UpdateCartRequest request);

    void emptyCart(UUID cartId);
}
