package com.fourman.banner.presentation.rest;

import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fourman.banner.application.dto.request.CreateBannerRequest;
import com.fourman.banner.application.dto.response.BannerDTO;
import com.fourman.common.dto.response.Response;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Banner API")
@RequestMapping("/api")
@Validated
public interface BannerController {

    @Operation(summary = "Create banner")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/banners")
    Response<BannerDTO> createBanner(@Valid @RequestBody CreateBannerRequest request);

    @Operation(summary = "Get all banners")
    @GetMapping("/banners")
    Response<List<BannerDTO>> getAllBanners();

    @Operation(summary = "Delete banner")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/banners/{id}")
    Response<Void> deleteBanner(@PathVariable UUID id);
}
