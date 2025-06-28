package com.evo.shopinfo.rest;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.evo.common.dto.response.ApiResponses;
import com.evo.common.dto.response.ShopAddressDTO;
import com.evo.shopinfo.application.dto.request.CreateOrUpdateShopAddressRequest;
import com.evo.shopinfo.application.service.ShopAddressCommandService;
import com.evo.shopinfo.application.service.ShopAddressQueryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ShopAddressController {
    private final ShopAddressCommandService shopAddressCommandService;
    private final ShopAddressQueryService shopAddressQueryService;

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/shop-address")
    public ApiResponses<ShopAddressDTO> updateShopAddress(@RequestBody CreateOrUpdateShopAddressRequest request) {
        ShopAddressDTO shopAddressDTO = shopAddressCommandService.update(request);
        return ApiResponses.<ShopAddressDTO>builder()
                .data(shopAddressDTO)
                .success(true)
                .code(200)
                .message("Shop address updated successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/shop-address")
    public ApiResponses<List<ShopAddressDTO>> getAllShopAddresses() {
        List<ShopAddressDTO> shopAddressDTOs = shopAddressQueryService.getAllShopAddresses();
        return ApiResponses.<List<ShopAddressDTO>>builder()
                .data(shopAddressDTOs)
                .success(true)
                .code(200)
                .message("Shop addresses retrieved successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }
}
