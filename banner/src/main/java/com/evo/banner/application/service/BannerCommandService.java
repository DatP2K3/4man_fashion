package com.evo.banner.application.service;

import java.util.UUID;

import com.evo.banner.application.dto.request.CreateBannerRequest;
import com.evo.banner.application.dto.response.BannerDTO;

public interface BannerCommandService {
    BannerDTO createBanner(CreateBannerRequest request);

    void deleteBanner(UUID id);
}
