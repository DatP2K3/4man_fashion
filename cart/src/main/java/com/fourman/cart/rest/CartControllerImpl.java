package com.fourman.cart.rest;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.*;

import com.fourman.cart.application.dto.request.UpdateCartRequest;
import com.fourman.cart.application.service.CartCommandService;
import com.fourman.cart.application.service.CartQueryService;
import com.fourman.common.dto.response.CartDTO;
import com.fourman.common.dto.response.Response;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CartControllerImpl implements CartController {
    private final CartQueryService cartQueryService;
    private final CartCommandService cartCommandService;

    @Override
    public Response<CartDTO> getCartOrInit() {
        return Response.of(this.cartCommandService.getOrInitCart());
    }

    @Override
    public Response<List<CartDTO>> getAllCarts() {
        return Response.of(this.cartQueryService.getAllCarts());
    }

    @Override
    public Response<CartDTO> updateCart(@RequestBody UpdateCartRequest updateCartRequest) {
        return Response.of(this.cartCommandService.updateCart(updateCartRequest));
    }

    @Override
    public Response<Void> emptyCart(@PathVariable UUID cartId) {
        this.cartCommandService.emptyCart(cartId);
        return Response.ok();
    }
}
