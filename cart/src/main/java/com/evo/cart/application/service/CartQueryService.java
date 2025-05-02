package com.evo.cart.application.service;

import com.evo.cart.application.dto.response.CartDTO;

import java.util.List;
import java.util.UUID;

public interface CartQueryService {
    List<CartDTO> getAllCarts();
}
