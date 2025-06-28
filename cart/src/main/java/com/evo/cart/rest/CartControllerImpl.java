package com.evo.cart.rest;

import java.util.List;
import java.util.UUID;

import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/carts/get-or-init")
    ApiResponses<CartDTO> getCartOrInit() {
        CartDTO cartDTO = cartCommandService.getOrInitCart();
        return ApiResponses.of(cartDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/carts")
    ApiResponses<List<CartDTO>> getAllCarts() {
        List<CartDTO> cartDTOs = cartQueryService.getAllCarts();
        return ApiResponses.of(cartDTOs);
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/carts")
    ApiResponses<CartDTO> updateCart(@RequestBody UpdateCartRequest updateCartRequest) {
        CartDTO updatedCart = cartCommandService.updateCart(updateCartRequest);
        return ApiResponses.of(updatedCart);
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/carts/empty/{cartId}")
    ApiResponses<Void> emptyCart(@PathVariable UUID cartId) {
        cartCommandService.emptyCart(cartId);
        return ApiResponses.ok();
    }
}
