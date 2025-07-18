package com.evo.profile.presentation.rest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.evo.common.dto.response.PagingResponse;
import com.evo.common.dto.response.ProfileDTO;
import com.evo.common.dto.response.Response;
import com.evo.profile.application.dto.request.CreateOrUpdateAddressRequest;
import com.evo.profile.application.dto.request.SearchProfileRequest;
import com.evo.profile.application.dto.request.UpdateProfileInfoRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Profile API")
@RequestMapping("/api")
@Validated
public interface ProfileController {

    @Operation(summary = "Initialize profile")
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/profiles")
    Response<ProfileDTO> initProfile();

    @Operation(summary = "Create shipping address")
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/profiles/shipping-address")
    Response<ProfileDTO> createShippingAddress(@RequestBody CreateOrUpdateAddressRequest request);

    @Operation(summary = "Update shipping address")
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/profiles/shipping-address")
    Response<ProfileDTO> updateShippingAddress(@RequestBody CreateOrUpdateAddressRequest request);

    @Operation(summary = "Update profile")
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/profiles")
    Response<ProfileDTO> updateProfile(@RequestBody UpdateProfileInfoRequest request);

    @Operation(summary = "Change avatar")
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/profiles/avatar")
    Response<ProfileDTO> changeAvatar(@RequestPart MultipartFile file);

    @Operation(summary = "Search profiles")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all-profiles")
    PagingResponse<ProfileDTO> search(SearchProfileRequest searchProfileRequest);
}
