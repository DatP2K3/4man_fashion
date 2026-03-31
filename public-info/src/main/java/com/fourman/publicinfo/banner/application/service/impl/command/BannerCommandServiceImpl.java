package com.fourman.publicinfo.banner.application.service.impl.command;

import java.util.UUID;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fourman.publicinfo.banner.application.dto.mapper.BannerDTOMapper;
import com.fourman.publicinfo.banner.application.dto.request.CreateBannerRequest;
import com.fourman.publicinfo.banner.application.dto.response.BannerDTO;
import com.fourman.publicinfo.banner.application.mapper.BannerCommandMapper;
import com.fourman.publicinfo.banner.application.service.BannerCommandService;
import com.fourman.publicinfo.banner.domain.Banner;
import com.fourman.publicinfo.banner.domain.command.CreateBannerCmd;
import com.fourman.publicinfo.banner.domain.repository.BannerDomainRepository;

import lombok.RequiredArgsConstructor;

@Transactional(rollbackFor = Exception.class)
@Service
@RequiredArgsConstructor
public class BannerCommandServiceImpl implements BannerCommandService {
    private final BannerCommandMapper commandMapper;
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
