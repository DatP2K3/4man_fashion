package com.evo.cart.rest;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.*;

import com.evo.cart.application.dto.request.UpdateCartRequest;
import com.evo.cart.application.service.CartCommandService;
import com.evo.cart.application.service.CartQueryService;
import com.evo.common.dto.response.ApiResponses;
import com.evo.common.dto.response.CartDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CartController {
    private final CartQueryService cartQueryService;
    private final CartCommandService cartCommandService;

    @GetMapping("/carts/get-or-init")
    ApiResponses<CartDTO> getCartOrInit() {
        CartDTO cartDTO = cartCommandService.getOrInitCart();
        return ApiResponses.<CartDTO>builder()
                .data(cartDTO)
                .success(true)
                .code(200)
                .message("Cart retrieved successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }

    @GetMapping("/carts")
    ApiResponses<List<CartDTO>> getAllCarts() {
        List<CartDTO> cartDTOs = cartQueryService.getAllCarts();
        return ApiResponses.<List<CartDTO>>builder()
                .data(cartDTOs)
                .success(true)
                .code(200)
                .message("Carts retrieved successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }

    @PutMapping("/carts")
    ApiResponses<CartDTO> updateCart(@RequestBody UpdateCartRequest updateCartRequest) {
        CartDTO updatedCart = cartCommandService.updateCart(updateCartRequest);
        return ApiResponses.<CartDTO>builder()
                .data(updatedCart)
                .success(true)
                .code(200)
                .message("Cart updated successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }

    @PutMapping("/carts/empty/{cartId}")
    ApiResponses<Void> emptyCart(@PathVariable UUID cartId) {
        cartCommandService.emptyCart(cartId);
        return ApiResponses.<Void>builder()
                .data(null)
                .success(true)
                .code(200)
                .message("Cart emptied successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }
}
