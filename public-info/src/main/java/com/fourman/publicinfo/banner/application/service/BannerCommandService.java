package com.fourman.publicinfo.banner.application.service;

import java.util.UUID;

import com.fourman.publicinfo.banner.application.dto.request.CreateBannerRequest;
import com.fourman.publicinfo.banner.application.dto.response.BannerDTO;

public interface BannerCommandService {
    BannerDTO createBanner(CreateBannerRequest request);

    void deleteBanner(UUID id);
}
