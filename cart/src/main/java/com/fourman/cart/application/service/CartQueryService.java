package com.fourman.cart.application.service;

import java.util.List;

import com.fourman.common.dto.response.CartDTO;

public interface CartQueryService {
    List<CartDTO> getAllCarts();
}
