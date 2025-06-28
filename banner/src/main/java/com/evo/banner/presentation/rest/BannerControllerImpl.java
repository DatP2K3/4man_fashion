package com.evo.banner.presentation.rest;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.*;

import com.evo.banner.application.dto.request.CreateBannerRequest;
import com.evo.banner.application.dto.response.BannerDTO;
import com.evo.banner.application.service.BannerCommandService;
import com.evo.banner.application.service.BannerQueryService;
import com.evo.common.dto.response.Response;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BannerControllerImpl implements BannerController {
    private final BannerCommandService bannerCommandService;
    private final BannerQueryService bannerQueryService;

    @Override
    public Response<BannerDTO> createBanner(@RequestBody CreateBannerRequest request) {
        return Response.of(this.bannerCommandService.createBanner(request));
    }

    @Override
    public Response<List<BannerDTO>> getAllBanners() {
        return Response.of(this.bannerQueryService.getAllBanners());
    }

    @Override
    public Response<Void> deleteBanner(@PathVariable UUID id) {
        this.bannerCommandService.deleteBanner(id);
        return Response.ok();
    }
}
