package com.fourman.banner.application.service;

import java.util.UUID;

import com.fourman.banner.application.dto.request.CreateBannerRequest;
import com.fourman.banner.application.dto.response.BannerDTO;

public interface BannerCommandService {
    BannerDTO createBanner(CreateBannerRequest request);

    void deleteBanner(UUID id);
}
