package com.fourman.publicinfo.banner.presentation.rest;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.*;

import com.fourman.publicinfo.banner.application.dto.request.CreateBannerRequest;
import com.fourman.publicinfo.banner.application.dto.response.BannerDTO;
import com.fourman.publicinfo.banner.application.service.BannerCommandService;
import com.fourman.publicinfo.banner.application.service.BannerQueryService;
import com.fourman.common.dto.response.Response;

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
