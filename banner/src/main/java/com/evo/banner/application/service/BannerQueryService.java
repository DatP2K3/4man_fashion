package com.evo.banner.application.service;

import java.util.List;

import com.evo.banner.application.dto.response.BannerDTO;

public interface BannerQueryService {
    List<BannerDTO> getAllBanners();
}
