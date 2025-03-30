package com.evo.profile.presentation.rest;

import org.springframework.web.bind.annotation.*;

import com.evo.common.dto.response.ApiResponses;
import com.evo.profile.application.dto.request.CreateOrUpdateProfileRequest;
import com.evo.profile.application.dto.response.ProfileDTO;
import com.evo.profile.application.dto.response.ShippingAddressDTO;
import com.evo.profile.application.service.ProfileCommandService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileCommandService profileCommandService;

    @PostMapping("/profiles/shipping-address")
    ApiResponses<ShippingAddressDTO> createShippingAddress(@RequestBody CreateOrUpdateProfileRequest request) {
        ShippingAddressDTO shippingAddressDTO = profileCommandService.createShippingAddress(request);
        return ApiResponses.<ShippingAddressDTO>builder()
                .data(shippingAddressDTO)
                .success(true)
                .code(201)
                .message("ShippingAddress created successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }

    @PutMapping("/profiles/shipping-address")
    ApiResponses<ShippingAddressDTO> updateShippingAddress(@RequestBody CreateOrUpdateProfileRequest request) {
        ShippingAddressDTO shippingAddressDTO = profileCommandService.updateShippingAddress(request);
        return ApiResponses.<ShippingAddressDTO>builder()
                .data(shippingAddressDTO)
                .success(true)
                .code(200)
                .message("ShippingAddress updated successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }

    @PutMapping("/profiles")
    ApiResponses<ProfileDTO> updateProfile(@RequestBody CreateOrUpdateProfileRequest request) {
        ProfileDTO profileDTO = profileCommandService.updateProfile(request);
        return ApiResponses.<ProfileDTO>builder()
                .data(profileDTO)
                .success(true)
                .code(200)
                .message("Profile updated successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }
}
