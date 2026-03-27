package com.fourman.cart.rest;

import java.util.List;
import java.util.UUID;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fourman.cart.application.dto.request.UpdateCartRequest;
import com.fourman.common.dto.response.CartDTO;
import com.fourman.common.dto.response.Response;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Cart API")
@RequestMapping("/api")
@Validated
public interface CartController {

    @Operation(summary = "Get cart or init cart")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/carts/get-or-init")
    Response<CartDTO> getCartOrInit();

    @Operation(summary = "Get all carts")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/carts")
    Response<List<CartDTO>> getAllCarts();

    @Operation(summary = "Update cart")
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/carts")
    Response<CartDTO> updateCart(@RequestBody UpdateCartRequest updateCartRequest);

    @Operation(summary = "Empty cart")
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/carts/empty/{cartId}")
    Response<Void> emptyCart(@PathVariable UUID cartId);
}
