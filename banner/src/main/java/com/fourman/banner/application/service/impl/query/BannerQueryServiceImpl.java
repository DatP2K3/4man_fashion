package com.fourman.banner.application.service.impl.query;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fourman.banner.application.dto.mapper.BannerDTOMapper;
import com.fourman.banner.application.dto.response.BannerDTO;
import com.fourman.banner.application.service.BannerQueryService;
import com.fourman.banner.domain.Banner;
import com.fourman.banner.domain.repository.BannerDomainRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BannerQueryServiceImpl implements BannerQueryService {
    private final BannerDTOMapper bannerDTOMapper;
    private final BannerDomainRepository bannerDomainRepository;

    @Override
    public List<BannerDTO> getAllBanners() {
        List<Banner> banners = bannerDomainRepository.getAll();
        return bannerDTOMapper.domainModelsToDTOs(banners);
    }
}
