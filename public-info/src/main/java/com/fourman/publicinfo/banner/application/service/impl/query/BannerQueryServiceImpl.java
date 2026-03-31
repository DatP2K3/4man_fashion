package com.fourman.publicinfo.banner.application.service.impl.query;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fourman.publicinfo.banner.application.dto.mapper.BannerDTOMapper;
import com.fourman.publicinfo.banner.application.dto.response.BannerDTO;
import com.fourman.publicinfo.banner.application.service.BannerQueryService;
import com.fourman.publicinfo.banner.domain.Banner;
import com.fourman.publicinfo.banner.domain.repository.BannerDomainRepository;

import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class BannerQueryServiceImpl implements BannerQueryService {
    private final BannerDTOMapper bannerDTOMapper;
    private final BannerDomainRepository bannerDomainRepository;

    @Override
    @Cacheable(value = "banners", unless = "#result == null || #result.isEmpty()")
    public List<BannerDTO> getAllBanners() {
        List<Banner> banners = bannerDomainRepository.getAll();
        return bannerDTOMapper.domainModelsToDTOs(banners);
    }
}
