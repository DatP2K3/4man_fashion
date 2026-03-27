package com.fourman.banner.application.service.impl.command;

import java.util.UUID;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import com.fourman.banner.application.dto.mapper.BannerDTOMapper;
import com.fourman.banner.application.dto.request.CreateBannerRequest;
import com.fourman.banner.application.dto.response.BannerDTO;
import com.fourman.banner.application.mapper.CommandMapper;
import com.fourman.banner.application.service.BannerCommandService;
import com.fourman.banner.domain.Banner;
import com.fourman.banner.domain.command.CreateBannerCmd;
import com.fourman.banner.domain.repository.BannerDomainRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BannerCommandServiceImpl implements BannerCommandService {
    private final CommandMapper commandMapper;
    private final BannerDTOMapper bannerDTOMapper;
    private final BannerDomainRepository bannerDomainRepository;

    @Override
    @CacheEvict(value = "banners", allEntries = true)
    public BannerDTO createBanner(CreateBannerRequest request) {
        CreateBannerCmd createBannerCmd = commandMapper.from(request);
        Banner banner = new Banner(createBannerCmd);
        banner = bannerDomainRepository.save(banner);
        return bannerDTOMapper.domainModelToDTO(banner);
    }

    @Override
    @CacheEvict(value = "banners", allEntries = true)
    public void deleteBanner(UUID id) {
        Banner banner = bannerDomainRepository.getById(id);
        banner.markAsDeleted();
        bannerDomainRepository.save(banner);
    }
}
