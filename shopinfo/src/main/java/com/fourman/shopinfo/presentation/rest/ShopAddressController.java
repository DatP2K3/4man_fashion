package com.fourman.shopinfo.presentation.rest;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fourman.common.dto.response.Response;
import com.fourman.common.dto.response.ShopAddressDTO;
import com.fourman.shopinfo.application.dto.request.CreateOrUpdateShopAddressRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Shop Address API")
@RequestMapping("/api")
@Validated
public interface ShopAddressController {

    @Operation(summary = "Update shop address")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/shop-address")
    Response<ShopAddressDTO> updateShopAddress(@Valid @RequestBody CreateOrUpdateShopAddressRequest request);

    @Operation(summary = "Get all shop addresses")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/shop-address")
    Response<List<ShopAddressDTO>> getAllShopAddresses();
}
