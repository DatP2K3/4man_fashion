package com.evo.banner.presentation.rest;

import java.util.List;
import java.util.UUID;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.evo.banner.application.dto.request.CreateBannerRequest;
import com.evo.banner.application.dto.response.BannerDTO;
import com.evo.banner.application.service.BannerCommandService;
import com.evo.banner.application.service.BannerQueryService;
import com.evo.common.dto.response.ApiResponses;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BannerController {
    private final BannerCommandService bannerCommandService;
    private final BannerQueryService bannerQueryService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/banners")
    public ApiResponses<BannerDTO> createBanner(@RequestBody CreateBannerRequest request) {
        BannerDTO bannerDTO = bannerCommandService.createBanner(request);
        return ApiResponses.<BannerDTO>builder()
                .data(bannerDTO)
                .success(true)
                .code(201)
                .message("Banner created successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }

    @GetMapping("/banners")
    public ApiResponses<List<BannerDTO>> getAllBanners() {
        List<BannerDTO> bannerDTOs = bannerQueryService.getAllBanners();
        return ApiResponses.<List<BannerDTO>>builder()
                .data(bannerDTOs)
                .success(true)
                .code(200)
                .message("Banners retrieved successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/banners/{id}")
    public ApiResponses<Void> deleteBanner(@PathVariable UUID id) {
        bannerCommandService.deleteBanner(id);
        return ApiResponses.<Void>builder()
                .success(true)
                .code(200)
                .message("Banner updated successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }
}
