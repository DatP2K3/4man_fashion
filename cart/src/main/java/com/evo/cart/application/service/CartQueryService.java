package com.evo.cart.application.service;

import java.util.List;

import com.evo.cart.application.dto.response.CartDTO;

public interface CartQueryService {
    List<CartDTO> getAllCarts();
}
