package com.evo.cart.application.service;

import com.evo.cart.application.dto.request.UpdateCartRequest;
import com.evo.cart.application.dto.response.CartDTO;

public interface CartCommandService {
    CartDTO getOrInitCart();
    CartDTO updateCart(UpdateCartRequest request);
}
