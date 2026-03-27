package com.fourman.banner.application.service;

import java.util.List;

import com.fourman.banner.application.dto.response.BannerDTO;

public interface BannerQueryService {
    List<BannerDTO> getAllBanners();
}
