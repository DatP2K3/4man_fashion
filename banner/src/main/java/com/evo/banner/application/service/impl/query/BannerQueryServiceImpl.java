package com.evo.banner.application.service.impl.query;

import java.util.List;

import org.springframework.stereotype.Service;

import com.evo.banner.application.dto.mapper.BannerDTOMapper;
import com.evo.banner.application.dto.response.BannerDTO;
import com.evo.banner.application.service.BannerQueryService;
import com.evo.banner.domain.Banner;
import com.evo.banner.domain.repository.BannerDomainRepository;

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
